package com.open.accountsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.Button;

import com.open.accountsoft.utils.SPUtils;
import com.vein.accountsoft.activity.R;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;

public class SplashActivity extends Activity {
	private Button splash_loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		splash_loginButton = (Button) findViewById(R.id.splash_btn);

		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(2000);
		animationSet.addAnimation(alphaAnimation);
		splash_loginButton.startAnimation(animationSet);
		XiaomiUpdateAgent.setCheckUpdateOnlyWifi(true);
		XiaomiUpdateAgent.update(this);

		splash_loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, Login.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
//		SPUtils.remove(this, "CaculatorReturn");
		SPUtils.put(this, "CaculatorReturn","0");
	}

}
