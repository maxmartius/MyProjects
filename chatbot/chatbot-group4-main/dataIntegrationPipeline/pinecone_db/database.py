import pinecone
import os
import tensorflow_hub as hub
from nltk.tokenize import sent_tokenize
import nltk
nltk.download('punkt')

class Database:
    def __init__(self, index_name):
        self.index_name = index_name

        api_key = os.getenv('PINECONE_API_KEY') or '4ab16de0-03e5-4a19-b744-10de90a5f07a'
        pinecone.init(
            api_key=api_key,
            environment="asia-southeast1-gcp-free"  # find next to API key in console
        )
        if index_name not in pinecone.list_indexes():
            dimensions = 512
            metadata_config = {
                "indexed": [
                    "lecture",
                    "type",
                    "slide"
                ]
            }
            pinecone.create_index(name=index_name, dimension=dimensions, metadata_config=metadata_config)
        self.index = pinecone.Index(index_name=self.index_name)

    def upsert_data(self, data):
        embed = hub.load("https://tfhub.dev/google/universal-sentence-encoder/4")
        count = 0
        vectors = []
        print("Data has " + str(len(data)) + " segments.")
        for i in data:
            sentences = sent_tokenize(i['paragraph'])
            embeddings = embed(sentences).numpy().tolist()
            for embedding, sentence in zip(embeddings, sentences):
                if (i['is_lecture']):
                    id = "lecture_" + i['lecture_name'] + "_" + str(count)
                    type = "lecture"
                else:
                    id = "article_" + i['lecture_name'] + "_" + str(count)
                    type = "article"
                vectors.append({
                    'id': id,
                    'values': embedding,
                    "metadata": {
                        "lecture": i["lecture_name"],
                        "type": type,
                        "paragraph": sentence
                    },
                })
                count = count + 1

        # with open('tmpvector.txt', 'w') as file:
        #     file.write(json.dumps(vectors))
        u = self.index.upsert(vectors=vectors, namespace="se4ai")

        print(u)
        print("Upserted " + str(u['upserted_count']) + " vectors to Pinecone.")

    def upsert_slides(self, lectures_slides):
        embed = hub.load("https://tfhub.dev/google/universal-sentence-encoder/4")
        vectors = []
        for lecture_slides in lectures_slides:
            lecture = lecture_slides["lecture"]
            slides = lecture_slides["slides"]
            embeddings = embed(slides).numpy().tolist()
            index = 1
            for embedding, slide in zip(embeddings, slides):
                v_id = "lecture_" + lecture + "_" + str(index)
                vectors.append({
                    'id': v_id,
                    'values': embedding,
                    "metadata": {
                        "lecture": lecture,
                        "type": "slide",
                        "slide": index,
                        "paragraph": slide
                    },
                })
                index = index + 1
        u = self.index.upsert(vectors=vectors, namespace="se4ai")
        print("Upserted " + str(u['upserted_count']) + " vectors to Pinecone.")


    def query_database(self, query, lecture, slide_number, top_k):
        embed = hub.load("https://tfhub.dev/google/universal-sentence-encoder/4")

        vector = embed([query]).numpy().tolist()
        
        if(slide_number == 0):
            filter = {"lecture": lecture, "type": {"$in": ["article", "lecture"]}}
        else:
            filter = {"lecture": lecture, "slide": slide_number, "type": "slide"}

        answers = self.index.query(vector=vector,
                                   namespace="se4ai",
                             filter=filter,
                             top_k=top_k,
                             include_values=True,
                             include_metadata=True)
        #print(answers)
        context = [answer['metadata']['paragraph'] for answer in answers['matches']]
        return context
