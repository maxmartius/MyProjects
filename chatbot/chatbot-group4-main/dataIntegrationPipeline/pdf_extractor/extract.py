import PyPDF2

segment_character_limit = 12000

def extract_text_from_pdf(path: str):
    slides = []
    pdf_file = open(path, 'rb')
    reader = PyPDF2.PdfReader(pdf_file)
    number_of_pages = len(reader.pages)

    for page_number in range(0, number_of_pages):
        page = reader.pages[page_number]
        page_content = page.extract_text()
        slides.append(page_content)

    character_count = len(" ".join(slides))
    if(character_count > segment_character_limit):
        segment_num = int(character_count / segment_character_limit) + 1
        segments = segment_slides(slides, segment_num)
    else:
        segments = ["".join(slides)]

    return segments, slides


def segment_slides(slides, segment_num):
    segments = []
    segment_length = int(len(slides)/segment_num) + 1
    for i in range(segment_num):
        start_seg = i * segment_length
        end_seg = (i+1) * segment_length
        segment = " ".join(slides[start_seg:end_seg])
        if len(segment) > segment_character_limit:
            middle = start_seg + int((end_seg-start_seg)/2)
            segments.append(" ".join(slides[start_seg:middle]))
            segments.append(" ".join(slides[middle:end_seg]))
        else:
            segments.append(segment)
    return segments
