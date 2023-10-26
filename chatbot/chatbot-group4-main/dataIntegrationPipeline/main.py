import json
import sys

from pdf_extractor.extract import extract_text_from_pdf
from scraper.crawl import crawl_industry_experiences
from summarizer.summarize import ask_language_model_to_find_topics_and_summarize
from summarizer.summarize import ask_language_model_to_summarize
from pinecone_db.database import Database


def incorporate_lectures(path, files):
    # Extract text from pdf lecture
    lectures = []
    lectures_slides = []
    for filename in files:
        lecture_parts, slides = extract_text_from_pdf(path + "/" + filename)
        topics = []
        segments = []
        for part in lecture_parts:
            t, s = ask_language_model_to_find_topics_and_summarize(part)
            if t is not None:
                topics.extend(t)
            if s is not None:
                segments.append(s)
        lectures.append({
            "lecture": filename[:-4],
            "topics": topics,
            "segments": segments,
            "count": len("".join(segments))
        })
        lectures_slides.append({
            "lecture": filename[:-4],
            "slides": slides,
        })

    print("Extracted, segmented and summarized lectures:")
    for lecture in lectures:
        print("lecture " + lecture['lecture'] + " with topics " + ", ".join(lecture['topics']) + " and length of "
              + str(lecture['count']) + " characters.")

    # Search for external resources of the subject and scrape them
    ## Collect topics from all lectures
    topics = sum([lecture['topics'] for lecture in lectures], [])


    ## Run crawler
    crawl_industry_experiences("medium", topics)

    ## Collect crawled articles from export and assign them to their respective lectures
    articles = []
    with open('../dataIntegrationPipeline/scraper/data.json') as scraped_batch:
        crawled_data = json.load(scraped_batch)
        for article in crawled_data:
            lecture_name = ""
            for lecture in lectures:
                if article['topic'] in lecture['topics']:
                    lecture_name = lecture['lecture']
                    break
            articles.append({
                "lecture_name": lecture_name,
                "paragraph": article['article_text']
            })
            print("Crawled article " + article['title'] if article['title'] is not None else "NO TITLE")

    with open('../dataIntegrationPipeline/scraper/data.json', 'w') as scraped_batch:
        scraped_batch.write('')

    # Summarize content using language model
    summarized_data = []

    ## Summarize lectures
    for lecture in lectures:
        for segment in lecture['segments']:
            summarized_data.append({
                "lecture_name": lecture['lecture'],
                "is_lecture": True,
                "paragraph": segment
            })
            # print("Summarized segment of lecture " + lecture['lecture'] + " from " + str(
            #     lecture['count']) + " characters to " +
            #       str(len(segment)) + " characters.")

    ## Summarize articles
    for article in articles:
        summary = ask_language_model_to_summarize(article['paragraph'])
        if summary is not None:
            summarized_data.append({
                "lecture_name": article['lecture_name'],
                "is_lecture": False,
                "paragraph": summary
            })
            print("Summarized article about lecture " + article['lecture_name'] + " from " + str(
                len(article['paragraph'])) +
                  " characters to " + str(len(summary)) + " characters.")

    with open('tmp.txt', 'w') as scraped_batch:
        scraped_batch.write(json.dumps(summarized_data))

    # Save contents in vector database
    db = Database("se4ai4")
    db.upsert_data(summarized_data)
    db.upsert_slides(lectures_slides)


def main(argv):
    path = "../dataIntegrationPipeline/lectures"
    incorporate_lectures(path, argv)


if __name__ == '__main__':
    main(sys.argv[1:])
