# chatbot-group4

## Intro

This project aims at creating a chatbot for making learning easier.
The user can provide learning materials in form of pdfs or use the already loaded 
sources to ask questions about certain topics, which then will be answered with the help of 
OpenAIs ChatGPT.

## What do you need?

Docker, Web Browser, working internet connection

## Setup

The chatbot interface will be running locally through a web application run from a docker container.
So you just install docker and download the container image.

## How to use

You simply run the web application through docker and the rest happens through a kubernetes cluster, where the 
actual chatbot is running on.

## Roadmap

- [x] create inital documentation (business plan, requirements engineering, c4-model)
- [x] get initial pipeline running 
    - [x] webscrapper
    - [x] handle database interaction (pinecone)
- [] create interface
- [] handle API calls for ChatGPT
- [] inference pipeline
- [] create feedback-loop
- [] tie everything together and get the chatbot running

## Contributing

---

## Authors

Fabian Goetz,
Mohammed Kheir Alawa,
Max Martius,
Philipp Baltik

## License
MIT License

## Project status

not finished.
