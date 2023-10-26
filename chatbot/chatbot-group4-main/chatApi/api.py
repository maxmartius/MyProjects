from dataIntegrationPipeline.pinecone_db.database import Database
from chatter.chat import send_message_to_model
import glob
import PyPDF2
import time
import tiktoken

def get_lectures():
    paths = glob.glob("../dataIntegrationPipeline/lectures/*.pdf")
    list = []
    for pdf in paths:
        page_num = len(PyPDF2.PdfReader(open(pdf, 'rb')).pages)
        #lecture_name = pdf.split("\\")[-1][:-4]
        lecture_name = pdf.split("/")[-1][:-4]
        list.append({"lecture_name": lecture_name, "page_num": page_num})

    return list

def make_request(request, lecture, slide, strategy):
    start = time.time()
    db = Database("se4ai4")

    if strategy == 'strict':
        top_k = 3
        temp = 0
    else:
        top_k = 10
        temp = 1.53

    context = db.query_database(request, lecture, slide, top_k)

    encoding = tiktoken.encoding_for_model("gpt-3.5-turbo")
    while True:
        token_count = len(encoding.encode(", ".join(context)))
        if token_count > 4000:
            context = context[:-1]
        else:
            break

    mid = time.time()
    answer = send_message_to_model(request, ", ".join(context), temp)
    end = time.time()

    with open("../dataIntegrationPipeline/summarizer/timeit.log", "a+") as myfile:
        myfile.write("question: " + request + " - lecture: " + lecture + " - strategy: " + strategy + "\n")
        myfile.write("Database Query took " + str(mid - start) + "seconds\n")
        myfile.write("Response from ChatGPT took " + str(end - mid) + "seconds\n\n")

    return answer
