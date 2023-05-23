import json
import urllib
from time import strftime, localtime
from telebot.types import InputMediaPhoto


def parser(request):
    arr_message = urllib.parse.unquote(request.text.replace("+", " ")).split(' ')
    new_msg = ''
    for i in arr_message:
        new_msg += ' '
        new_msg += i
    r = json.loads(json.dumps(new_msg), strict=False)
    r = json.loads(r)
    ans = []
    i = 0
    for msg in r:
        if msg["images"]:
            media_group = []
            for photo_url in msg["images"]:
                media_group.append(InputMediaPhoto(media=photo_url))
        else:
            media_group = []
        ans.append(['#' + str(i + 1) + '\n' + msg["text"] + '\n' + strftime('%Y-%m-%d %H:%M:%S', localtime(int(msg["time"]))), media_group])
        i += 1
    return ans
