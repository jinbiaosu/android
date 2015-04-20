package com.vein.translater.fragment;

import com.vein.translater.R;
import com.vein.translater.db.TranslateDAO;
import com.vein.translater.db.model.TB_Translate;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TranslateHistoryFranment extends Fragment {
	private EditText search_content;
	private Button searchbtn;
	private TextView showTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.history, container, false);
		initView(mView);
		searchbtn.setOnClickListener(new SearchBtnListener());
		return mView;
	}

	private void initView(View mView) {
		search_content = (EditText) mView.findViewById(R.id.search_content);
		searchbtn = (Button) mView.findViewById(R.id.search_btn);
		showTextView = (TextView) mView.findViewById(R.id.show);
	}

	class SearchBtnListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			TB_Translate result = new TranslateDAO(getActivity()).find();
			System.out.println("vein:"+result.getInput());
			String inputString = result.getInput();
			String resuString = result.getTresult();
			System.out.println("vein:"+inputString+resuString);
			showTextView.setText(inputString + "\n" + resuString);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		showTextView.setText("");
	}
}
