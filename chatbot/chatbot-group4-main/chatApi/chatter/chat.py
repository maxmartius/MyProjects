import requests
import json
from datetime import datetime


def send_message_to_model(question, context, temp):
    url = "https://chat.sws.informatik.uni-leipzig.de/chat_completion"
    api_key = "986c23c6-f651-4519-9a19-8d3ca7a62f84"

    with open("../chatApi/chatter/chat-message-template.json") as template:
        data = json.load(template)
    data["temperature"] = temp
    message = "Please answer the following question: \"" + question + "\" Based on the following context: " + context
    data["messages"].append({"role": "user", "content": message})

    req = requests.post(url, headers={'Authorization': api_key}, json=data)
    log_communication(json.dumps(data), "stream answer")
    #req.json()["choices"][0]["message"]["content"]

    return req



def log_communication(data, response):
    with open("../dataIntegrationPipeline/summarizer/communication.log", "a") as myfile:
        dt = datetime.now().strftime("%m/%d/%Y, %H:%M:%S")
        myfile.write("Request at " + dt + ": " + data + "\n")
        myfile.write("Response at " + dt + ": " + response + "\n")
