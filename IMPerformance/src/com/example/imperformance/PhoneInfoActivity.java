package com.example.imperformance;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.imperformance.utils.Utils;

public class PhoneInfoActivity extends Activity {
	private TextView show;
	private Button showbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		show = (TextView) findViewById(R.id.show);
		String buildSystemInfo=Utils.buildSystemInfo(getApplicationContext());
		show.setText(buildSystemInfo);
	}
}
