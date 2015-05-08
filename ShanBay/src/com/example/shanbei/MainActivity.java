package com.example.shanbei;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.shanbei.service.ShanBeiService;

public class MainActivity extends Activity {
	WebView mView;
	Button getTokenButton, getInfoButton;
	TextView tokenTextView;
	private static String TAG = "vein";
	Map<String, String> map = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startService(new Intent(MainActivity.this, ShanBeiService.class));
		authAccountWebView();
		init();
		map.put("access_token", "6iD12gQauaY2djHUzyhpR9Z4cDiQjg");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void init() {
		getTokenButton = (Button) findViewById(R.id.getTokenBtn);
		tokenTextView = (TextView) findViewById(R.id.getTokenTV);
		getInfoButton = (Button) findViewById(R.id.getInfo);
		getTokenButton.setOnClickListener(new GetTokenListener());
		getInfoButton.setOnClickListener(new GetInfoListener());

	}

	public void authAccountWebView() {
		mView = (WebView) findViewById(R.id.auth_account);
		mView.loadUrl("https://api.shanbay.com/oauth2/authorize/?client_id=dc56af0030c618625062&response_type=token&redirect_uri=https://api.shanbay.com/oauth2/auth/success/");
		mView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

	}

	class GetTokenListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String tokenUrl = mView.getUrl();
			Log.d(TAG, "url : " + tokenUrl);
			try {
				tokenUrl = tokenUrl.substring(tokenUrl.indexOf("=") + 1,
						tokenUrl.indexOf("&"));
				tokenTextView.setText(tokenUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Log.d(TAG, "url1 : " + tokenUrl);

		}
	}

	class GetInfoListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			new MyThread().start();
		}
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			String resultString = null;
			String word = null;
			try {
				resultString = HttpUtils
						.doHttpsGet(ShanBeiConstant.URL_ACCOUNT_INFO);
				word = queryWord(ShanBeiConstant.URL_QUERY_WORD, "good");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("info", resultString);
			data.putString("word", word);
			msg.setData(data);
			handler.sendMessage(msg);

		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String resultString = data.getString("info");
			String word = data.getString("word");// 获取翻译数据
			word=getWordTranslate(word);
			Log.d(TAG, resultString);
			Log.d(TAG, word + "");
		}
	};

	public String getWordTranslate(String jsonString) {
		String resultString = null;
		JSONObject dataJsonObject=JSONObject.parseObject(jsonString);
		Log.d(TAG, "dataJsonObject : "+dataJsonObject);

		String definition=dataJsonObject.getString("data");
		JSONObject definitionJsonObject=JSONObject.parseObject(definition);
		resultString=definitionJsonObject.getString("definition");
		Log.d("definition", resultString);
		return resultString;

	}

	public String queryWord(String url, String word) {
		String resultString = null;
		try {
			resultString = HttpUtils.doHttpsGet(url + word);
		} catch (Exception e) {

			e.printStackTrace();
		}
		if ("".equals(resultString) || resultString == null) {
			return resultString;
		}
		return resultString;
	}
}
