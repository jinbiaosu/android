package com.example.shanbei.service;

import com.alibaba.fastjson.JSONObject;
import com.example.shanbei.HttpUtils;
import com.example.shanbei.SPUtils;
import com.example.shanbei.ShanBeiConstant;

import android.annotation.SuppressLint;
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

			@SuppressWarnings("deprecation")
			@Override
			public void onPrimaryClipChanged() {
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
			String word=null;
			try {
				String tokenString=SPUtils.getString(getApplicationContext(), "token");
				String queryUrlString=null;
				if(tokenString!=null){
					queryUrlString=ShanBeiConstant.URL_QUERY_WORD+tokenString+"&word=";
				}
				if (queryUrlString!=null) {
					word=queryWord(queryUrlString);	
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("word", word);
			msg.setData(data);
			handler.sendMessage(msg);

		}
	}
	@SuppressLint("HandlerLeak") Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String word=data.getString("word");
			Log.d(TAG, word + "");
			jsonResultString=word;
			if(getWordTranslate(jsonResultString)==null){
				Toast.makeText(getApplicationContext(), "you input is not a english word !!", Toast.LENGTH_LONG).show();
				return;
			}
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
		try {
			resultString=JSONObject.parseObject(JSONObject.parseObject(jsonString).getString("data")).getString("definition");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Log.d("definition", resultString+"");
		return resultString;

	}
}
