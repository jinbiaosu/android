package com.vein.translater.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vein.translater.db.model.TB_Translate;

public class TranslateDAO {
	private TranslateOpenHelper openHelper;
	private SQLiteDatabase mdDatabase;

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
