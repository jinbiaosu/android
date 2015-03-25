package com.example.translatedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.baidutranslate.openapi.TranslateClient;
import com.baidu.baidutranslate.openapi.callback.ITransResultCallback;
import com.baidu.baidutranslate.openapi.entity.Language;
import com.baidu.baidutranslate.openapi.entity.TransResult;

public class MainActivity extends Activity {

	private String API_KEY = "X9q2L9z2GnfM7jGtxGTGkmAK";
	private EditText translate_from;
	private TextView translate_to;
	private Button translate;
	private Spinner mSpinner_origin,mSpinner_dest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TranslateClient client = new TranslateClient(this, API_KEY);
		init();
		//create data sources
		String[] mitems_origin=getResources().getStringArray(R.array.origin_data);
		//create adapter and bind data
		ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,mitems_origin);
		//bind adapter to spinner
		mSpinner_origin.setAdapter(mAdapter);
		
		String[] mitems_dest=getResources().getStringArray(R.array.dest_data);
		ArrayAdapter<String> mAdapter_dest=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,mitems_dest);
		mSpinner_dest.setAdapter(mAdapter_dest);
		
		
		translate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(((mSpinner_origin.getSelectedItem().toString()).equals("英语"))&&((mSpinner_dest.getSelectedItem().toString()).equals("中文"))){
					client.translate(translate_from.getText().toString(),
							Language.ENGLISH, Language.CHINESE,
							new ITransResultCallback() {

								@Override
								public void onResult(TransResult result) {// 翻译结果回调
									if (result == null) {
										Log.d("TransOpenApiDemo",
												"Trans Result is null");
									} else {
										Log.d("TransOpenApiDemo", "MainActivity->"
												+ result.toJSONString());
										try {
											
											JSONObject mJsonObject=JSONArray.parseObject(result.toJSONString());
											String result_dest =mJsonObject.getString("trans_result");
											translate_to.setText(result_dest);
										} catch (Exception e) {
											
										}
									}
								}
							});
				}else if (((mSpinner_origin.getSelectedItem().toString()).equals("英语"))&&((mSpinner_dest.getSelectedItem().toString()).equals("日语"))) {
					client.translate(translate_from.getText().toString(),
							Language.ENGLISH, Language.JAPANESE,
							new ITransResultCallback() {

								@Override
								public void onResult(TransResult result) {// 翻译结果回调
									if (result == null) {
										Log.d("TransOpenApiDemo",
												"Trans Result is null");
									} else {
										Log.d("TransOpenApiDemo", "MainActivity->"
												+ result.toJSONString());
										try {
											
											JSONObject mJsonObject=JSONArray.parseObject(result.toJSONString());
											String result_dest =mJsonObject.getString("trans_result");
											translate_to.setText(result_dest);
										} catch (Exception e) {
											
										}
									}
								}
							});
				}else if (((mSpinner_origin.getSelectedItem().toString()).equals("中文"))&&((mSpinner_dest.getSelectedItem().toString()).equals("英语"))) {
					client.translate(translate_from.getText().toString(),
							Language.JAPANESE, Language.ENGLISH,
							new ITransResultCallback() {

								@Override
								public void onResult(TransResult result) {// 翻译结果回调
									if (result == null) {
										Log.d("TransOpenApiDemo",
												"Trans Result is null");
									} else {
										Log.d("TransOpenApiDemo", "MainActivity->"
												+ result.toJSONString());
										try {
											
											JSONObject mJsonObject=JSONArray.parseObject(result.toJSONString());
											String result_dest =mJsonObject.getString("trans_result");
											translate_to.setText(result_dest);
										} catch (Exception e) {
											
										}
									}
								}
							});
				}else if (((mSpinner_origin.getSelectedItem().toString()).equals("中文"))&&((mSpinner_dest.getSelectedItem().toString()).equals("日语"))) {
					client.translate(translate_from.getText().toString(),
							Language.CHINESE, Language.JAPANESE,
							new ITransResultCallback() {

								@Override
								public void onResult(TransResult result) {// 翻译结果回调
									if (result == null) {
										Log.d("TransOpenApiDemo",
												"Trans Result is null");
									} else {
										Log.d("TransOpenApiDemo", "MainActivity->"
												+ result.toJSONString());
										try {
											
											JSONObject mJsonObject=JSONArray.parseObject(result.toJSONString());
											String result_dest =mJsonObject.getString("trans_result");
											translate_to.setText(result_dest);
										} catch (Exception e) {
											
										}
									}
								}
							});
				}else {
					Toast.makeText(getApplicationContext(), "哎呀，这个老师没教我怎么翻译哇！", Toast.LENGTH_SHORT).show();
				}
				
			}
		});

	}

	private void init() {
		translate_from = (EditText) findViewById(R.id.orginal_text);
		translate_to = (TextView) findViewById(R.id.translate_text);
		translate = (Button) findViewById(R.id.translate);
		mSpinner_origin=(Spinner)findViewById(R.id.origin_langue);
		mSpinner_dest=(Spinner)findViewById(R.id.dest_langue);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
