package com.vein.myandroidtools;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		System.err.println("aaaaaaaaaaaa");
		Toast.makeText(getApplicationContext(), "start service",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		System.err.println("sssssssssssssssss");
		Toast.makeText(getApplicationContext(), "destory service",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return Service.START_CONTINUATION_MASK;
	}

}
