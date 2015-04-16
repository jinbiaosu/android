package com.vein.translater.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vein.translater.R;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

@SuppressLint("NewApi")
public class MainTab04 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.main_tab_04, container,
				false);
		return newsLayout;
	}

	@Override
	public void onResume() {
		super.onResume();
		MiStatInterface.recordPageStart(getActivity(), "设备界面");
	}

	@Override
	public void onPause() {
		super.onPause();
		MiStatInterface.recordPageEnd();
	}
}
