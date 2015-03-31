package com.vein.myutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesUtil {
	public final static String NAME = "Setting";

	public static void putInt(Context context, String key, int value) {
		Editor editor = context
				.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void putBoolean(Context context, String key, boolean value) {
		Editor editor = context
				.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void putString(Context context, String key, String value) {
		Editor editor = context
				.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static int getInt(Context context, String key, int defValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				NAME, Context.MODE_PRIVATE);
		int value = sharedPreferences.getInt(key, defValue);
		return value;
	}

	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		SharedPreferences spsharedPreferences = context.getSharedPreferences(
				NAME, Context.MODE_PRIVATE);
		boolean value = spsharedPreferences.getBoolean(key, defValue);
		return value;
	}

	public static String getString(Context context, String key, String defValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				NAME, Context.MODE_PRIVATE);
		String value = sharedPreferences.getString(key, defValue);
		return value;
	}

}
