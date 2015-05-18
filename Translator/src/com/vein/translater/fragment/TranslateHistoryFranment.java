package com.vein.translater.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.vein.translater.R;
import com.vein.translater.db.TranslateDAO;
import com.vein.translater.db.model.TB_Translate;

public class TranslateHistoryFranment extends Fragment {
	private EditText search_content;
	private Button searchbtn;
	private TextView showTextView;
	private ListView dataListView;
	ArrayList<TB_Translate> dataArrayList=null;
	private final static String TAG=TranslateHistoryFranment.class.getSimpleName();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.history, container, false);
		initView(mView);
		searchbtn.setOnClickListener(new SearchBtnListener());
//		dataArrayList=new TranslateDAO(getActivity()).findAll();
//		dataListView.setAdapter(new DataAdapter(getActivity()));
		dataArrayList=new TranslateDAO(getActivity()).findAll();
		dataListView.setAdapter(new SimpleAdapter(getActivity(), getAllData(),
				R.layout.data_list_item, new String[]{"orginal","translate"}, new int[]{R.id.orginal,R.id.translate}));
		return mView;
	}

	private void initView(View mView) {
		search_content = (EditText) mView.findViewById(R.id.search_content);
		searchbtn = (Button) mView.findViewById(R.id.search_btn);
		showTextView = (TextView) mView.findViewById(R.id.show);
		dataListView=(ListView)mView.findViewById(R.id.data_list);
	}

	class SearchBtnListener implements OnClickListener {
		@Override
		public void onClick(View v) {
//			TB_Translate result = new TranslateDAO(getActivity()).find();
//			if(result==null){
//				Toast.makeText(getActivity(), "No data", Toast.LENGTH_LONG).show();
//				return;
//			}
//			String inputString = result.getInput();
//			String resuString = result.getTresult();
//			Log.d(TAG, "input : "+inputString);
//			Log.d(TAG, "result : "+resuString);
//
//			showTextView.setText(inputString + "\n" + resuString);
			//get all data
//			ArrayList<TB_Translate> list = new TranslateDAO(getActivity()).findAll();
//			if(list==null){
//				Toast.makeText(getActivity(), "No data", Toast.LENGTH_LONG).show();
//				return;
//			}
//			StringBuffer sBuffer=new StringBuffer();
//			for (int i = 0; i < list.size(); i++) {
//				String inputString=list.get(i).getInput();
//				String resultString=list.get(i).getTresult();
//				sBuffer.append(inputString+":"+resultString+"\n");
//			}
//			Log.d(TAG, "All data : "+sBuffer.toString());
//			showTextView.setText(sBuffer.toString());
			
//			dataListView.setAdapter(new ArrayAdapter<String>(getActivity(),
//					android.R.layout.simple_dropdown_item_1line,getData()));
			dataArrayList=new TranslateDAO(getActivity()).findByWord(search_content.getText().toString().trim());
			dataListView.setAdapter(new SimpleAdapter(getActivity(), getAllData(),
					R.layout.data_list_item, new String[]{"orginal","translate"}, new int[]{R.id.orginal,R.id.translate}));
			dataListView.clearTextFilter();
		}
	}
	private ArrayList<String > getData(){
		ArrayList<String > data=new ArrayList<String>();
		dataArrayList=new TranslateDAO(getActivity()).findAll();
		for (int i = 0; i < dataArrayList.size(); i++) {
			data.add(dataArrayList.get(i).getInput()+":"+dataArrayList.get(i).getTresult());
		}
		return data;
	}
	private List<Map<String, String>> getAllData(){
		List<Map<String, String>>  list=new ArrayList<Map<String,String>>();
		Map<String, String> map=null;
		dataArrayList=new TranslateDAO(getActivity()).findAll();
		for (int i = 0; i < dataArrayList.size(); i++) {
			map=new HashMap<String, String>();
			map.put("orginal", dataArrayList.get(i).getInput());
			map.put("translate", " : "+dataArrayList.get(i).getTresult());
			list.add(map);
		}
		return list;
	}
	@Override
	public void onResume() {
		super.onResume();
		showTextView.setText("");
	}
	public class DataAdapter extends BaseAdapter{
		private LayoutInflater mInflater=null;
		private DataAdapter(Context context){
			this.mInflater=LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			if(dataArrayList==null){
				return 0;
			}
			return dataArrayList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ViewHolder holder=null;
			if(convertView==null){
				holder=new ViewHolder();
				convertView=mInflater.inflate(R.layout.data_list_item, null);
				holder.dataTextView=(TextView)convertView.findViewById(R.id.orginal);
				convertView.setTag(holder);
			}else {
				holder=(ViewHolder)convertView.getTag();
			}
			holder.dataTextView.setText(dataArrayList.get(position).getInput()
					+":"
					+dataArrayList.get(position).getTresult());
			return convertView;
		}
		
	}
	static class ViewHolder{
		public TextView dataTextView;
	}
}
