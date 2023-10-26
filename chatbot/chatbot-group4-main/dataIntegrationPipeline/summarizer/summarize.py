import requests
import json
from datetime import datetime
import os


def send_message_to_model(message, system_message="You are a helpful assistant.", temp=1.53):
    url = "https://chat.sws.informatik.uni-leipzig.de/chat_completion"
    api_key = "986c23c6-f651-4519-9a19-8d3ca7a62f84"

    with open("../dataIntegrationPipeline/summarizer/message-template.json") as template:
        data = json.load(template)
    data["messages"].append({"role": "system", "content": system_message})
    data["messages"].append({"role": "user", "content": message})
    data["temperature"] = temp
    req = requests.post(url, headers={'Authorization': api_key}, json=data)
    log_communication(json.dumps(data), req.text)

    return req


def ask_language_model_to_summarize(text):
    req = send_message_to_model("Please Summarize the following text: \"" + text + "\"")
    try:
        response = req.json()["choices"][0]["message"]["content"]
    except KeyError:
        print("ERROR: Text too long!")
        response = None
    return response


def ask_language_model_to_find_topics_and_summarize(text):
    system_message = "You are ChatGPT, a large language model trained to identify the main topics of a text. Please provide the main topics in JSON format."
    message = "I have a lengthy text, and I'd like you to identify its main topics. Text: \"" + text + "\""
    req = send_message_to_model(message, system_message, 1.0)
    response = req.json()["choices"][0]["message"]["content"].replace("\n", "").replace("\t", "").replace("\u00a0", "")
    try:
        result = json.loads(response[response.find('{'):response.rfind('}') + 1])
        topics = result["topics"]
        print(topics)
    except json.decoder.JSONDecodeError:
        print("ERROR: Language model did not return topics in correct format!")
        topics = None

    summary = ask_language_model_to_summarize(text)
    print(summary)

    return topics, summary


def log_communication(data, response):
    with open("../dataIntegrationPipeline/summarizer/communication.log", "a") as myfile:
        dt = datetime.now().strftime("%m/%d/%Y, %H:%M:%S")
        myfile.write("Request at " + dt + ": " + data + "\n")
        myfile.write("Response at " + dt + ": " + response + "\n")
