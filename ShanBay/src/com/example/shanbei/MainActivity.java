package com.example.shanbei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shanbei.service.ShanBeiService;

public class MainActivity extends Activity {
	WebView mView;
	Button getTokenButton, getInfoButton;
	TextView tokenTextView;
	public final static String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startService(new Intent(MainActivity.this, ShanBeiService.class));
		authAccountWebView();
	}

	public void authAccountWebView() {
		String urlString="https://api.shanbay.com/oauth2/authorize/?client_id="+ShanBeiConstant.APP_KEY+"&response_type=token&redirect_uri=https://api.shanbay.com/oauth2/auth/success/";
		mView = (WebView) findViewById(R.id.auth_account);
		mView.loadUrl(urlString);
		mView.setWebViewClient(new WebViewClient() {
	
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				Log.d(TAG, "reload url:"+url);
				String tokenString=getTokenString(url);
				if(tokenString!=null&&!"".endsWith(tokenString)){
					SPUtils.putString(getApplicationContext(), "token",tokenString );
				}else {
					Toast.makeText(getApplicationContext(), "Write Token error !!", Toast.LENGTH_LONG).show();
				}
				Log.d(TAG, "getString : "+SPUtils.getString(getApplicationContext(), "token"));
				return true;
			}
		});

	}
	public String getTokenString(String tokenString){
		String token=tokenString;
		try {
			token = token.substring(token.indexOf("=") + 1,
					token.indexOf("&"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d(TAG, "token is : "+token);
		return token;
	}

}
