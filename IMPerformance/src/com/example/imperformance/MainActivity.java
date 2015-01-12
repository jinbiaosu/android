package com.example.imperformance;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_list);

		List<String> items = fillList();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);

		setListAdapter(adapter);

	}

	private List<String> fillList() {
		List<String> items = new ArrayList<String>();

		items.add("Devics Info");
		items.add("All APP");
		items.add("test");
		items.add("test");
		items.add("test");
		items.add("test");
		items.add("test");
		return items;

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent;
		switch (position) {
		case 0:
			intent = new Intent(MainActivity.this, PhoneInfoActivity.class);
			startActivity(intent);
			// intent = new Intent();
			// intent.setClass(getApplicationContext(),
			// PhoneInfoActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// getApplicationContext().startActivity(intent);
			break;
		case 1:
			intent = new Intent(MainActivity.this, AppInfoActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}