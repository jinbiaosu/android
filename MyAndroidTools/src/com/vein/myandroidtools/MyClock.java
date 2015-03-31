package com.vein.myandroidtools;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MyClock extends Activity {
	private TextView mtextView = null;
	private static final int SET = 1;

	private ProgressBar bar;
	private TextView bar_TV;
	private EditText bar_et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.myclock);
		init();
		new Thread(new ClockThread()).start();
		new MyProgressBar().execute(100);
		ClipboardManager clipboardManager=(ClipboardManager)super.getSystemService(Context.CLIPBOARD_SERVICE);
		clipboardManager.setText("aaaaaaaaaaaaa");
		NotificationManager notificationManager=(NotificationManager)super.getSystemService(Activity.NOTIFICATION_SERVICE);
		Notification notification=new Notification(R.drawable.ic_launcher, "测试通知 ", System.currentTimeMillis());
		PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, super.getIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(this, "测试", "sdfsdfsfs", pendingIntent);
		notificationManager.notify("sssss", R.drawable.ic_launcher, notification);
//		bar_et.setOnClickListener(new MyClipboardManager());
	}

//	private class MyClipboardManager implements OnClickListener {
//		@Override
//		public void onClick(View v) {
//			ClipboardManager
//		}
//	}

	private void init() {
		mtextView = (TextView) findViewById(R.id.myclock);
		bar = (ProgressBar) findViewById(R.id.bar);
		bar_TV = (TextView) findViewById(R.id.bar_tv);
		bar_et = (EditText) findViewById(R.id.bar_et);

	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SET:
				if (msg.obj.toString() != null) {
					mtextView.setText("当前系统时间是:" + msg.obj.toString());
				}
				break;

			default:
				break;
			}
		};
	};

	private class ClockThread implements Runnable {
		@SuppressLint("SimpleDateFormat")
		@Override
		public void run() {
			while (true) {
				Message message = MyClock.this.mHandler.obtainMessage(SET,
						new SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
								.format(new Date()));
				MyClock.this.mHandler.sendMessage(message);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private class MyProgressBar extends AsyncTask<Integer, Integer, String> {
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			MyClock.this.bar_TV.setText(result);

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			MyClock.this.bar_TV.setText("当前进度是" + String.valueOf(values[0]));

		}

		@Override
		protected String doInBackground(Integer... params) {
			for (int i = 0; i < 100; i++) {
				MyClock.this.bar.setProgress(i);
				this.publishProgress(i);
				try {
					Thread.sleep(params[0]);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return "执行完毕";
		}
	}
}
