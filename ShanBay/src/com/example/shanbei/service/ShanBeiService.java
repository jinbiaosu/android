package com.example.shanbei.service;

import com.alibaba.fastjson.JSONObject;
import com.example.shanbei.HttpUtils;
import com.example.shanbei.ShanBeiConstant;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ShanBeiService extends Service {
	private static final String TAG = ShanBeiService.class.getSimpleName();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

		super.onCreate();
		final ClipboardManager cb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		cb.setPrimaryClip(ClipData.newPlainText("", ""));
		cb.addPrimaryClipChangedListener(new OnPrimaryClipChangedListener() {

			@Override
			public void onPrimaryClipChanged() {
//				Toast.makeText(getApplicationContext(), cb.getText().toString(), Toast.LENGTH_LONG).show();
				wordSelectedString=cb.getText().toString();
				new MyThread1().start();
			}
		});
	}
	public String wordSelectedString;
	public String jsonResultString;
	class MyThread1 extends Thread {
		@Override
		public void run() {
			String resultString = null;
			String word=null;
			try {
//				resultString = HttpUtils.doHttpsGet(ShanBeiConstant.URL_ACCOUNT_INFO);
				word=queryWord(ShanBeiConstant.URL_QUERY_WORD);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Message msg = new Message();
			Bundle data = new Bundle();
//			data.putString("info", resultString);
			data.putString("word", word);
			msg.setData(data);
			handler.sendMessage(msg);

		}
	}
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
//			String resultString = data.getString("info");
			String word=data.getString("word");
//			Log.d(TAG, resultString + "");
			Log.d(TAG, word + "");
			jsonResultString=word;
			Toast.makeText(getApplicationContext(), getWordTranslate(jsonResultString), Toast.LENGTH_LONG).show();
		}
	};

	public String queryWord(String url) {
		String resultString = null;
		try {
			resultString = HttpUtils.doHttpsGet(url
					+ wordSelectedString);
		} catch (Exception e) {

			e.printStackTrace();
		}
		if ("".equals(resultString) || resultString == null) {
			return resultString;
		}
		Log.d(TAG, "query : "+resultString);
		return resultString;
	}
	public String getWordTranslate(String jsonString) {
		String resultString = null;
//		JSONObject dataJsonObject=JSONObject.parseObject(jsonString);
//		Log.d(TAG, "dataJsonObject : "+dataJsonObject);
//
//		String definition=dataJsonObject.getString("data");
//		JSONObject definitionJsonObject=JSONObject.parseObject(definition);
//		resultString=definitionJsonObject.getString("definition");
//		
		
		resultString=JSONObject.parseObject(JSONObject.parseObject(jsonString).getString("data")).getString("definition");
		Log.d("definition", resultString);
		return resultString;

	}
}
