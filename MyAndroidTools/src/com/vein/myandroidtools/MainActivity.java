package com.vein.myandroidtools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView mtexView;

	private ListView lv;
	private List<Map<String, Object>> data;

	public static final int ToastMsg = 0;
	public static final int message = 1;

	private Handler mHandler = new Handler() {
		int count = 0;

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case ToastMsg:
				mtexView.setText("这是第" + count++ + "次点击第2个button");
				break;

			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mtexView = (TextView) findViewById(R.id.mytext);

		lv = (ListView) findViewById(R.id.lv);
		// 获取将要绑定的数据设置到data中
		data = getData();
		// MyAdapter adapter = new MyAdapter(this);
		// lv.setAdapter(adapter);
		lv.setAdapter(new MyAdapter(this));
		lv.setOnItemClickListener(new MyListener());
	}

	class MyListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg2 == 0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MyClock.class);
				startActivity(intent);
			}
			if (arg2 == 1) {
				// Toast.makeText(getApplicationContext(), "sfafsdf",
				// Toast.LENGTH_SHORT).show();

				Message message = new Message();
				message.what = ToastMsg;
				mHandler.sendMessage(message);
			}
			if (arg2 == 2) {
				MainActivity.this.startService(new Intent(MainActivity.this,
						MyService.class));
			}
			if (arg2 == 3) {
				MainActivity.this.stopService(new Intent(MainActivity.this,
						MyService.class));
			}
		}

	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (int i = 0; i < 20; i++) {
			map = new HashMap<String, Object>();
			if (i == 0) {
				map.put("img", R.drawable.ic_launcher);
				map.put("title", "点击查看时钟+进度条显示");
				map.put("info", "~~~~~~~~~~~");
				map.put("info2", "~~~~~~~~~~~");
			} else if (i == 1) {
				map.put("img", R.drawable.ic_launcher);
				map.put("title", "点击后最上方textview会变化");
				map.put("info", "22222222222222");
				map.put("info2", "333333333");
			}  else if (i ==2) {
				map.put("img", R.drawable.ic_launcher);
				map.put("title", "启动service");
				map.put("info", "22222222222222");
				map.put("info2", "333333333");
			} else if (i == 3) {
				map.put("img", R.drawable.ic_launcher);
				map.put("title", "关闭service");
				map.put("info", "22222222222222");
				map.put("info2", "333333333");
			} else {
				map.put("img", R.drawable.ic_launcher);
				map.put("title", "111111111111");
				map.put("info", "22222222222222");
				map.put("info2", "333333333");
			}
			list.add(map);
		}
		return list;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView info;
		public TextView info2;
	}

	public class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater = null;

		private MyAdapter(Context context) {
			// 根据context上下文加载布局，这里的是MainActivity本身，即this
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// How many items are in the data set represented by this Adapter.
			// 在此适配器中所代表的数据集中的条目数
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// Get the data item associated with the specified position in the
			// data set.
			// 获取数据集中与指定索引对应的数据项
			return position;
		}

		@Override
		public long getItemId(int position) {
			// Get the row id associated with the specified position in the
			// list.
			// 获取在列表中与指定索引对应的行id
			return position;
		}

		// Get a View that displays the data at the specified position in the
		// data set.
		// 获取一个在数据集中指定索引的视图来显示数据
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			// 如果缓存convertView为空，则需要创建View
			if (convertView == null) {
				holder = new ViewHolder();
				// 根据自定义的Item布局加载布局
				convertView = mInflater.inflate(R.layout.mylist_item, null);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.title = (TextView) convertView.findViewById(R.id.tv);
				holder.info = (TextView) convertView.findViewById(R.id.info);
				holder.info2 = (TextView) convertView.findViewById(R.id.info2);
				// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.img.setBackgroundResource((Integer) data.get(position).get(
					"img"));
			holder.title.setText((String) data.get(position).get("title"));
			holder.info.setText((String) data.get(position).get("info"));
			holder.info2.setText((String) data.get(position).get("info2"));

			return convertView;
		}

	}

}
