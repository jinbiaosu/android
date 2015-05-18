package com.vein.translater.db;

import java.util.ArrayList;

import android.R.string;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vein.translater.db.model.TB_Translate;

public class TranslateDAO {
	private TranslateOpenHelper openHelper;
	private SQLiteDatabase mdDatabase;
	private final static String TAG=TranslateDAO.class.getSimpleName();
	public TranslateDAO(Context context) {
		openHelper = new TranslateOpenHelper(context);
	}

	public void add(TB_Translate tb_Translate) {
		mdDatabase = openHelper.getWritableDatabase();
		mdDatabase.execSQL(
				"insert into tb_translate (input,tresult) values (?,?)",
				new Object[] { tb_Translate.getInput(),
						tb_Translate.getTresult() });
	}

	public void update(TB_Translate tb_Translate) {
		mdDatabase = openHelper.getWritableDatabase();

		mdDatabase.execSQL("update tb_translate set tresult = ? where input=test",
				new Object[] { tb_Translate.getTresult() });

	}

	public TB_Translate find() {
		mdDatabase = openHelper.getWritableDatabase();
		Cursor cursor = mdDatabase.rawQuery("select input,tresult from tb_translate",
				null);
		if (cursor.moveToNext()) {
			return new TB_Translate(cursor.getString(cursor
					.getColumnIndex("input")), cursor.getString(cursor
					.getColumnIndex("tresult")));
		}
		return null;
	}
	public ArrayList<TB_Translate> findAll(){
		ArrayList<TB_Translate> list=new ArrayList<TB_Translate>();
		mdDatabase=openHelper.getWritableDatabase();
		Cursor cursor=mdDatabase.rawQuery("select input,tresult from tb_translate", null);
		while (cursor.moveToNext()) {
		list.add(new TB_Translate(cursor.getString(cursor.getColumnIndex("input")), cursor.getString(cursor
				.getColumnIndex("tresult"))));
			
		}
		Log.d(TAG, "data length : "+list.size());
		return list;
	}
	public ArrayList<TB_Translate> findByWord(String word){
		ArrayList<TB_Translate> list=new ArrayList<TB_Translate>();
		mdDatabase=openHelper.getWritableDatabase();
		Cursor cursor=mdDatabase.rawQuery("select input,tresult from tb_translate where input like '%"+word+"%'", null);
		while (cursor.moveToNext()) {
		list.add(new TB_Translate(cursor.getString(cursor.getColumnIndex("input")), cursor.getString(cursor
				.getColumnIndex("tresult"))));
			
		}
		Log.d(TAG, "data length : "+list.size());
		return list;
	}
	public long getCount() {
		mdDatabase = openHelper.getWritableDatabase();
		Cursor cursor = mdDatabase.rawQuery("select count(input) from tb_translate", null);
		if (cursor.moveToNext())
		{
			return cursor.getLong(0);
		}
		return 0;
	}
}
