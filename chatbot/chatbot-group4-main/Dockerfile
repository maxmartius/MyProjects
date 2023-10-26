FROM ubuntu:22.04

WORKDIR /app
#COPY ./flask/requirements.txt /app
#RUN pip install -r requirements.txt

RUN apt-get update

RUN apt-get install -y python3.10
RUN apt-get install -y python3-pip
RUN python3 --version

#RUN echo "alias list='ls -cl --group-directories-first'" >> ~/.bashrc
#RUN source ~/.bashrc
RUN ln -s /usr/bin/python3 /usr/bin/python
RUN python --version
#COPY ./requirements.txt /app
COPY . .
RUN pip install -r requirements.txt --ignore-installed
RUN playwright install
RUN playwright install-deps

EXPOSE 5000
WORKDIR /app/flask
ENV FLASK_APP=main.py
CMD [ "python3", "-m" , "flask", "run", "--host=0.0.0.0", "--port=5000"]
#CMD ["flask", "run", "--host", "0.0.0.0","--port","5000"]