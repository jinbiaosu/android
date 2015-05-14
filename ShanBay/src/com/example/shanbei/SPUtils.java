package com.example.shanbei;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtils {
	public final static String TAG = SPUtils.class.getSimpleName();

	public static void putString(Context context, String key, String values) {
		Editor editor = context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
		editor.putString(key, values);
		editor.commit();
	}

	public static String getString(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
		return sp.getString(key, "");

	}
}
