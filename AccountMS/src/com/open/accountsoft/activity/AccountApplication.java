package com.open.accountsoft.activity;

import android.app.Application;
import android.util.Log;

import com.open.accountsoft.utils.AccountConstant;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

public class AccountApplication extends Application {

	private String appID = AccountConstant.APP_ID;
	private String appKey = AccountConstant.APP_KEY;

	@Override
	public void onCreate() {
		super.onCreate();
		// CrashHandler crashHandler = CrashHandler.getInstance();
		// crashHandler.init(getApplicationContext());

		MiStatInterface.initialize(this.getApplicationContext(), appID, appKey,
				"default channel");
		MiStatInterface.setUploadPolicy(
				MiStatInterface.UPLOAD_POLICY_WIFI_ONLY, 0);
		MiStatInterface.enableLog();
		MiStatInterface.enableExceptionCatcher(true);
		Log.d("MI_STAT", MiStatInterface.getDeviceID(this) + " is the device.");
	}

}
