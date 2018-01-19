package com.wizinno.apollo.common.sms;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wizinno.apollo.common.utils.HttpInvoker;
import com.wizinno.apollo.common.utils.RandomUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 模板短信DEMO
 *
 */
public class TemplateSms {
	public static String APP_ID = "158405570000269935";//应用ID------登录平台在应用设置可以找到
	public static String APP_SECRET = "00785fbc193b72475485537482e5c6a4";//应用secret-----登录平台在应用设置可以找到
	public static String ACCESS_TOKEN = "";//访问令牌AT-------CC模式，AC模式都可，推荐CC模式获取令牌
	public static String TEMPLATE_ID = "91553421";//模板ID



	//第一步根据app_id，app_secret获取令牌接口
	private static String getAccessToken() throws Exception {
		Gson gson = new Gson();
		String postUrl = "https://oauth.api.189.cn/emp/oauth2/v3/access_token?grant_type=client_credentials&app_id="
				+ APP_ID + "&app_secret=" + APP_SECRET;
		String resJson1 = HttpInvoker.httpPost(postUrl, null, null);
		System.err.println(resJson1);
		Map<String, String> map1 = gson.fromJson(resJson1,
				new TypeToken<Map<String, String>>() {
				}.getType());
		return map1.get("access_token").toString();
	}

	
	public static String sendSms(String tel) throws Exception {

		ACCESS_TOKEN=getAccessToken();

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String timestamp = dateFormat.format(date);
		System.err.println(timestamp);
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		//这里存放模板参数，如果模板没有参数直接用template_param={}
		map.put("param1", tel);
		map.put("param2", RandomUtil.randomFor6());
		map.put("param3","3" );




        String template_param = gson.toJson(map);
		System.out.println(template_param);
		String postUrl = "http://api.189.cn/v2/emp/templateSms/sendSms";
		
		String postEntity = "app_id=" + APP_ID + "&access_token="
				+ ACCESS_TOKEN + "&acceptor_tel=" + tel + "&template_id="
				+ TEMPLATE_ID + "&template_param=" + template_param
				+ "&timestamp=" + URLEncoder.encode(timestamp, "utf-8");
		System.out.println(postUrl);
		System.out.println(postEntity);
		String resJson = "";
		String idertifier = null;
		Map<String, String> map2 =null;
		try {
			resJson = HttpInvoker.httpPost1(postUrl, null, postEntity);
			map2 = gson.fromJson(resJson,
					new TypeToken<Map<String, String>>() {
					}.getType());
			idertifier = map2.get("idertifier").toString();
		} catch (IOException e) {
			System.err.println(resJson);
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(resJson);
			e.printStackTrace();
		}
		System.err.println(resJson);
		return map.get("param2");

	}



	/**
	 * @param args
	 */
/*
	public static void main(String[] args) {
		String result = "";
        try {
				result = sendSms("13696655620");
				System.out.println(result);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
*/


/*	public static void main(String[] args) {
		String result = "";
		try {
			result = checkMsg("90611013112342536772","914103","13696655620");
			System.out.println(result);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
