#!python3
# coding: utf-8

import requests, random, os,sys
import time
def refreshCaptcha():
	url = "http://c2020502194rsy.scd.wezhan.cn/Common/GenerateCommentCaptcha"
	myheaders = {
		"Cookie": "yibu_rt_language=zh-CN; ASP.NET_SessionId=zehh3mndik3o5oeynn5pe0nm; __RequestVerificationToken=WgJnwrfswdgo-I4j_F7a6LpoU9HeniDdG0Vbg2rOgwSRWaAXASvV67zRcgLb0WLjpVgPzY0fzPp5-GpyQJZlM7ry63iSujDsOIpsryBdl741; acw_tc=781bad0b16169177065446661e51423afadeceb60e8520165bc7351d1e8a11; SERVERID=9cce0917ca076d8ead327ae4668516bf|1616917784|1616917111",
		"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36"
	}
	timestamp = format(random.random(), ".16f")
	captchaResp = requests.get(url, headers=myheaders, params={"Timestamp": timestamp})
	# 获取验证码图片
	filename = "{}.jpg".format("captcha")
	with open(filename, "wb") as file:
		file.write(captchaResp.content)
	# 将文件保存到data文件夹下
	file_jpg = 'data/'+str(time.time()) + '.jpg'
	with open(file_jpg, "wb") as file:
		file.write(captchaResp.content)
	print("Captcha image is : {}".format(os.path.abspath(filename)))
	return timestamp

def addComment(captchanum, comment, timestamp):
	url = "http://c2020502194rsy.scd.wezhan.cn/Comment/AddComment"
	comment_headers = {
		"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36",
		"X-Requested-With": "XMLHttpRequest",
		"Cookie": "yibu_rt_language=zh-CN; ASP.NET_SessionId=zehh3mndik3o5oeynn5pe0nm; __RequestVerificationToken=WgJnwrfswdgo-I4j_F7a6LpoU9HeniDdG0Vbg2rOgwSRWaAXASvV67zRcgLb0WLjpVgPzY0fzPp5-GpyQJZlM7ry63iSujDsOIpsryBdl741; acw_tc=781bad0b16169239350794725e513f23f81a57f4a0c5f45e49db4694011377; SERVERID=9cce0917ca076d8ead327ae4668516bf|1616924822|1616924768",
		"Referer": "http://c2020502194rsy.scd.wezhan.cn/lyhd",
		"Origin": "http://c2020502194rsy.scd.wezhan.cn"
	}
	comment_payload = {
		"CommentText": comment,
		"Captcha": captchanum,
		"EntityId": 293515,
		"EntityType": 1,
		"Timestamp": timestamp, #这个值要跟请求验证码时候的Timestamp保持一致
		"__RequestVerificationToken": "WDw2cS0TSvXskn8kehzGX_Ixp_J_1fr4Mmb7_ETkCFYlMK5mwCrRXcNwS4lcVgByupVYNJehEIthw_pIntPkwmV2RSuiR5uufTlAt5TxGoo1"
	}

	resp = requests.post(url, headers=comment_headers, data=comment_payload)
	# print("Got response : {}".format(resp.text))
	print(resp.json()['IsSuccess'])

capnum = sys.argv[1]
comment = sys.argv[2]
timestamp = sys.argv[3]
addComment(capnum, comment, timestamp)
	# capnum = "a723"
	# comment = "测试评论"
	# timestamp = "0.3147280830624921"
	# addComment(capnum, comment, timestamp)