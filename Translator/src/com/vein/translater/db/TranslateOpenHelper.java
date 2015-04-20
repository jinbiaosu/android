package com.vein.translater.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TranslateOpenHelper extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	private static final String DBNAME = "translate.db";

	public TranslateOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table tb_translate(input varchar(100),tresult varchar(200))");
		db.execSQL("create table tb_dic(input varchar(100),dresult varchar(200))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
