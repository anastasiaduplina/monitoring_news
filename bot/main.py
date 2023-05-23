import json
import telebot
import requests
import random
import time
import urllib.parse
from prs import parser

TELEGRAM_TOKEN = ''
bot = telebot.TeleBot(TELEGRAM_TOKEN)
url = 'http://localhost:8082/'

@bot.message_handler(commands=['help'])
def help(message):
    file = open('help', encoding='utf-8', mode='r').readlines()
    str_ans = ''
    for string in file:
        str_ans += string
    bot.send_message(message.chat.id, str_ans)


@bot.message_handler(commands=['start'])
def welcome(message):
    user_id = str(message.from_user.username)
    payload = {'login': user_id, 'password': '12345'}
    r = requests.get(url + "addUser", params=payload)
    new_s = urllib.parse.unquote(r.text)
    bot.send_message(message.chat.id,
                     "Привет! Я бот для мониторинга новостей!")
    bot.send_message(message.chat.id,
                     new_s)


@bot.message_handler(commands=['new_request'])
def request(message):
    arr_message = message.text.split(' ')[1:]
    s = ""
    for i in range(len(arr_message)):
        s += arr_message[i]
        s += " "
    num = random.randint(1, 100000000000)
    payload = {'keyword': s, 'count': 2, 'uuid': num}
    requests.get(url + "getNews/filter", params=payload)
    msg = "not done"
    user_id = str(message.from_user.username)
    while msg == "not done":
        time.sleep(5)
        payload = {'uuid': num, 'login': user_id}
        r = requests.get(url + "request/getNewsByFilter", params=payload)
        if 200 <= r.status_code <= 229:
            if r.text != "not done":
                msg = urllib.parse.unquote(r.text.replace("+", " "))
                ans = parser(r)
                for i in ans:
                    bot.send_message(message.chat.id, i[0])
                    if i[1]:
                        bot.send_media_group(message.chat.id, i[1])
        else:
            msg = "Что-то пошло не так, попробуйте позже"
            bot.send_message(message.chat.id,
                             msg)

@bot.message_handler(commands=['change_password'])
def request(message):
    arr_message = message.text.split(' ')[1:]
    user_id = str(message.from_user.username)
    payload = {'passwordOld': arr_message[0], 'login': user_id, 'passwordNew': arr_message[1]}
    r = requests.get(url + "change/password", params=payload)
    if 200 <= r.status_code <= 229:
        msg = r.text
    else:
        msg = "Что-то пошло не так, попробуйте позже"
    bot.send_message(message.chat.id,
                     msg)


@bot.message_handler(commands=['add_keyword'])
def welcome(message):
    arr_message = message.text.split(' ')[1:]
    s = ""
    for i in range(len(arr_message)):
        s += arr_message[i]
        s += " "
    user_id = str(message.from_user.username)
    payload = {'login': user_id, 'keyword': s}
    r = requests.get(url + "addKeyword", params=payload)
    if 200 <= r.status_code <= 229:
        bot.send_message(message.chat.id,
                         "Ключевое слово добавлено")
        while True:
            time.sleep(15)
            payload = {'keyword': s, 'login': user_id}
            r = requests.get(url + "getNewNews", params=payload)
            if 200 <= r.status_code <= 229:
                msg = urllib.parse.unquote(r.text.replace("+", " "))
                if msg == "null":
                    break
                if msg != "[]":
                    ans=parser(r)
                    bot.send_message(message.chat.id, s + '\n')
                    for i in ans:
                        bot.send_message(message.chat.id, i[0])
                        if i[1]:
                            bot.send_media_group(message.chat.id, i[1])
            else:
                msg = "Что-то пошло не так, попробуйте позже"
                bot.send_message(message.chat.id, msg)
    else:
        msg = "Что-то пошло не так, попробуйте позже"
        bot.send_message(message.chat.id, msg)



@bot.message_handler(commands=['delete_keyword'])
def welcome(message):
    arr_message = message.text.split(' ')[1:]
    s = ""
    for i in range(len(arr_message)):
        s += arr_message[i]
        s += " "
    payload = {'keyword': s}
    requests.get(url + "deleteKeyword", params=payload)
    bot.send_message(message.chat.id, "Слово удалено")


@bot.message_handler(commands=['get_keywords'])
def request(message):
    user_id = str(message.from_user.username)
    payload = {'login': user_id}
    r = requests.get(url + "getAllKeywords", params=payload)
    if 200 <= r.status_code <= 229:
        msg = urllib.parse.unquote(r.text.replace("+", " "))
        msg = json.loads(msg)
        if msg:
            ans = msg[0][:-1]
            for i in range(1, len(msg)):
                ans += ', ' + msg[i][:-1]
            bot.send_message(message.chat.id, ans)
        else:
            bot.send_message(message.chat.id, "Нет ключевых слов")
    else:
        msg = "Что-то пошло не так, попробуйте позже"
        bot.send_message(message.chat.id, msg)



bot.polling(none_stop=True)
