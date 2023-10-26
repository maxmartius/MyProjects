from flask import Flask, render_template, request, url_for, redirect, session, escape, Response, g
from werkzeug.utils import secure_filename
from flask_sqlalchemy import SQLAlchemy
import sys
import os, time
sys.path.insert(0, '../')
sys.path.insert(0, '../dataIntegrationPipeline/')
sys.path.insert(0, '../chatApi/')
from dataIntegrationPipeline import main
from chatApi import api
from os import urandom
import json


app = Flask(__name__)

#comment block
#javascript mit localstorage // ->sessionstorage
#session limit 4KB (flask)
#javascript sessionstorage 5MB

# questions and answers are stored locally in sessionstorage of webbrowser -> close tab = all gone

Upload_folder = "."
app.config['UPLOAD_FOLDER'] = Upload_folder
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///se4ai_project.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)
extensions = set(['pdf'])

def event_stream(ask, course, slide, strat):
        print("retrieving answer ...")
        chunk = ""
        for line in api.make_request(ask, course, slide, strat):
            text = line.decode(encoding='utf-8', errors='strict') 
            chunk += text
            if "}{" in chunk:
                arr = chunk.split("}{")
                s1 = arr[0] + "}"
                s2 = "{" + arr[1]
                content = json.loads(s1)["choices"][0]["delta"]["content"]
                chunk = s2
                # letzter text ist empty und wird daher ignoriert und nicht zo content
                #content ist einzelnes wort
                if len(content):
                    yield content
                    time.sleep(0.1)

class Feedback(db.Model):
	id = db.Column(db.Integer, primary_key=True)
	rating = db.Column(db.Integer, nullable=True)
	feedback = db.Column(db.String, nullable=True)
	question = db.Column(db.String, nullable=False)
	answer = db.Column(db.String, nullable=False)

class Tracking(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    userId = db.Column(db.Integer, nullable=False)
    question = db.Column(db.String, nullable=True)
    lecture = db.Column(db.String, nullable=True)

def make_db_entry_2(id,q,l):
    new_entry = Tracking(
        userId = id,
        question = q,
        lecture = l
    )
    return new_entry
def create_db():
    with app.app_context():
	    db.create_all()

def save_to_db(entry):
	with app.app_context():
		db.session.add(entry)
		db.session.commit()

def make_db_entry(r,f,q,a):
	new_entry = Feedback(
				rating = r,
				feedback = f,
                question = q,
                answer = a
				)
	return new_entry


#checks for allowed filetype for upload
def allowed(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in extensions

#index route
@app.route("/", methods=['GET', 'Post'])
def index_page():
    if 'course' in session:
        return redirect(url_for('chat_page'))
    if request.method == 'POST':
        session['userID'] = urandom(6)
        lecture = request.form['course'].strip()
        session['course'] = lecture
        num = int(request.form['pagenumber'].strip())
        session['page'] = num
        #print("index: ",session)
        return redirect(url_for('chat_page'))
    if request.method == 'GET':
        lectures = api.get_lectures()
        return render_template('index_choice.html', lectures=lectures)

#chat route
@app.route("/chat", methods=['GET', 'POST'])
def chat_page():
    print("chat-session: ", session)
    return render_template('chat.html', course=session['course'], pages=session['page'])

#feedback route
@app.route("/feedback", methods=['POST', 'GET'])
def feddback_page():
    if request.method == 'POST':
        rating = 0
        feddback = ""
        question = ""
        answer = ""
        if 'rate' in request.form:
            rating = int(request.form['rate'])
        if 'feedback' in request.form:
            feddback = request.form['feedback']
        if 'question' in request.form:
            question = request.form['question']
        if 'answer' in request.form:
            answer = request.form['answer']
        if rating != 0 or feddback != "":
            save_to_db(make_db_entry(rating, feddback, question, answer))
    return render_template('feedback.html')

#upload route 
@app.route("/upload", methods=['GET', 'POST'])
def upload_page():
    if request.method == 'POST':
        #if no file was send just reload the startpage
        if 'file' not in request.files:
            return redirect(url_for('chat_page'))
        
        file = request.files['file']
        print(file)

        #if no file has been chossen
        if file.filename == '':
            print("file has no name")
            return redirect(url_for('chat_page'))
        
        # if file is allowed to be uploaded then secure it 
        # and continue
        if allowed(file.filename):
            filename = secure_filename(file.filename)
            print(file.filename)
            path = '../dataIntegrationPipeline/lectures/'
            file_path = path + filename
            file.save(file_path)
            while not os.path.exists(file_path):
                time.sleep(1)

            if os.path.isfile(file_path):
                os.system("python ../dataIntegrationPipeline/main.py " + filename)
            else:
                raise ValueError("%s isn't a file!" % file_path)

            return redirect(url_for('chat_page'))
        
    return render_template('upload.html')

#session logout for manual //session get erased when browser closses
@app.route("/logout")
def logout():
    session.pop('userID', None)
    session.pop('course', None)
    session.pop('page', None)
    return redirect(url_for('index_page'))

@app.route("/rechoose")
def kill_course():
    session.pop('course', None)
    session.pop('page', None)
    return redirect(url_for('index_page'))

@app.route('/completion', methods=['GET', 'POST'])
def completion_api():
    if request.method == "POST":
        data = request.form
        ask = data['ask']
        course = session['course']
        page = int(session["page"])
        save_to_db(make_db_entry_2(int.from_bytes(session["userID"], "big"), ask, course)) 
        strat = data.get("strategy")
        return Response(event_stream(ask, course, page, strat), mimetype='text/event-stream')
    else:
        return Response(None, mimetype='text/event-stream')

# possible generation of key os.urandom(24)
app.secret_key = "\xd8\x10\x88\xf6#\xfd\xa3\xfd\t\xa0%h\x86\xd5 \xe9x.\xe0\xb6>\xa7%R"

if __name__ == "__main__":
    if not os.path.isfile("/instance/se4ai.db"):
        create_db()
    app.run(debug=True)