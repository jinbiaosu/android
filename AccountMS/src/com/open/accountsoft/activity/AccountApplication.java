package com.open.accountsoft.activity;

import android.app.Application;

import com.open.accountsoft.utils.CrashHandler;

public class AccountApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		 CrashHandler crashHandler = CrashHandler.getInstance(); 
		 crashHandler.init(getApplicationContext());  
	}
}
