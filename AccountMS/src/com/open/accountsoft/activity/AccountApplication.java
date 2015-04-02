package com.open.accountsoft.activity;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.open.accountsoft.utils.AccountConstant;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

public class AccountApplication extends Application {

	private String appID = AccountConstant.APP_ID;
	private String appKey = AccountConstant.APP_KEY;
	public final static String TAG = AccountApplication.class.getName();

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
		// 初始化push推送服务
		if (shouldInit()) {
			MiPushClient.registerPush(this.getApplicationContext(),
					AccountConstant.APP_ID, AccountConstant.APP_KEY);
		}
		// 打开Log
		LoggerInterface newLogger = new LoggerInterface() {

			@Override
			public void setTag(String tag) {
				// ignore
			}

			@Override
			public void log(String content, Throwable t) {
				Log.d(TAG, content, t);
			}

			@Override
			public void log(String content) {
				Log.d(TAG, content);
			}
		};
		Logger.setLogger(this, newLogger);
	}

	private boolean shouldInit() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		// int myPid = Process.myPid();
		int myPid = android.os.Process.myPid();
		for (RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}

}
