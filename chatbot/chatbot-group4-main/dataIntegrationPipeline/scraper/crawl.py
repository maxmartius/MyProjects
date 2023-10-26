import scrapy
from scrapy.crawler import CrawlerProcess
from bs4 import BeautifulSoup
import urllib.parse


class Scraper(scrapy.Spider):
    name = "scraper"
    custom_settings = {
        'DOWNLOAD_HANDLERS': {
            "http": "scrapy_playwright.handler.ScrapyPlaywrightDownloadHandler",
            "https": "scrapy_playwright.handler.ScrapyPlaywrightDownloadHandler",
        },
        'TWISTED_REACTOR': "twisted.internet.asyncioreactor.AsyncioSelectorReactor"
    }

    def __init__(self, *args, **kwargs):
        super(Scraper, self).__init__(*args, **kwargs)

        self.start_urls = kwargs.get('start_urls')
        self.topic_list = kwargs.get('topics')

    def start_requests(self):
        for i in range(len(self.start_urls)):
            yield scrapy.Request(url=self.start_urls[i], meta={'playwright': True, 'topic': self.topic_list[i]}, callback=self.parse_search)

    def parse_search(self, response):
        search_results = response.selector.xpath("//article//a[descendant::h2]")
        link = search_results.xpath("@href").get()

        yield scrapy.Request("https://medium.com" + link, meta={'playwright': True, 'topic': response.meta['topic']}, callback=self.parse_page)

    def parse_page(self, response):
        article_html = response.selector.xpath('//article').get()
        soup = BeautifulSoup(article_html, "html.parser")
        article_text = soup.get_text(separator=" ")

        yield {
            'topic': response.meta['topic'],
            'title': response.selector.xpath("//h1/text()").get(),
            'article_text': article_text,
            'count': len(article_text)
        }


def crawl_industry_experiences(source, topics):
    match source:
        case "medium":
            return medium_crawler(topics)
        case _:
            return ""


def medium_crawler(topics):
    process = CrawlerProcess({'FEED_FORMAT': 'json',
                              'FEED_URI': '../dataIntegrationPipeline/scraper/data.json'})

    urls = []
    for topic in topics:
        base_url = "https://medium.com/"
        search_path = "search/posts?q="
        urls.append(base_url + search_path + urllib.parse.quote_plus(topic))

    process.crawl(Scraper, input='inputargument', start_urls=urls, topics=topics)
    process.start()


if __name__ == '__main__':
    medium_crawler(['Requirements Engineering for AI-Enabled Systems', 'Requirements & Specification (Basics)'])
