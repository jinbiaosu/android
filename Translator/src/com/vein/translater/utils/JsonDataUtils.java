package com.vein.translater.utils;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class JsonDataUtils {
	public static String getDicResultString(String jsonString)
			throws JSONException, Exception {
		JSONObject jsondata = JSONObject.parseObject(jsonString);
		JSONArray jsonArray = JSONArray.parseArray(JSONObject.parseObject(
				jsondata.getString("data").toString()).getString("symbols"));

		JSONObject jsonObject = JSONObject.parseObject(jsonArray.get(0)
				.toString());
		JSONArray resultArray = JSONArray.parseArray(jsonObject
				.getString("parts"));
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < resultArray.size(); i++) {

			String partString = JSONObject
					.parseObject(resultArray.getString(i)).getString("part");
			String meansString = JSONObject.parseObject(
					resultArray.getString(i)).getString("means");
			meansString = meansString.substring(2, meansString.length() - 2);
			meansString = meansString.replace("\",\"", ";");
			stringBuffer.append(partString + " " + meansString + "\n");
		}
		return stringBuffer.toString();
	}
}
