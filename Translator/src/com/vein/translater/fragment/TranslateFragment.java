package com.vein.translater.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.baidutranslate.openapi.TranslateClient;
import com.baidu.baidutranslate.openapi.callback.IDictResultCallback;
import com.baidu.baidutranslate.openapi.callback.ITransResultCallback;
import com.baidu.baidutranslate.openapi.entity.DictResult;
import com.baidu.baidutranslate.openapi.entity.Language;
import com.baidu.baidutranslate.openapi.entity.TransResult;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.vein.translater.R;
import com.vein.translater.utils.LangueUtils;
import com.vein.translater.utils.BooleanUtils;

@SuppressLint("NewApi")
public class TranslateFragment extends Fragment {
	private String API_KEY = "X9q2L9z2GnfM7jGtxGTGkmAK";
	TranslateClient client;
	private EditText translate_content;
	private TextView trabslate_result;
	private Button langue_translate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		client = new TranslateClient(getActivity(), API_KEY);
		View mView = inflater.inflate(R.layout.translate, container, false);
		langue_spinner = (Spinner) mView.findViewById(R.id.langue_spinner);

		translate_content = (EditText) mView
				.findViewById(R.id.translate_content);
		trabslate_result = (TextView) mView.findViewById(R.id.trabslate_result);
		langue_translate = (Button) mView.findViewById(R.id.langue_translate);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.langueselect,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
		langue_spinner.setAdapter(adapter);
		langue_spinner.setOnItemSelectedListener(new LaungueSelectListener());
		langue_translate.setOnClickListener(new TranslateListener());
		return mView;

	}

	private Spinner langue_spinner;
	int translateType;

	class LaungueSelectListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View mview, int arg2,
				long arg3) {
			switch (arg2) {
			case 0:
				Toast.makeText(getActivity(), "自动检测语言", Toast.LENGTH_SHORT)
						.show();
				trabslate_result.setText("译文：" + "\n");
				translateType = 0;
				break;
			case 1:
				Toast.makeText(getActivity(), "英文->中文", Toast.LENGTH_SHORT)
						.show();
				trabslate_result.setText("译文：" + "\n");
				translateType = 1;
				break;
			case 2:
				Toast.makeText(getActivity(), "中文->英文", Toast.LENGTH_SHORT)
						.show();
				trabslate_result.setText("译文：" + "\n");
				translateType = 2;
				break;
			case 3:
				Toast.makeText(getActivity(), "日语->中文", Toast.LENGTH_SHORT)
						.show();
				trabslate_result.setText("译文：" + "\n");
				translateType = 3;
				break;
			case 4:
				Toast.makeText(getActivity(), "中文->日语", Toast.LENGTH_SHORT)
						.show();
				trabslate_result.setText("译文：" + "\n");
				translateType = 4;
				break;
			case 5:
				Toast.makeText(getActivity(), "韩文->中文", Toast.LENGTH_SHORT)
						.show();
				trabslate_result.setText("译文：" + "\n");
				translateType = 5;
				break;
			case 6:
				Toast.makeText(getActivity(), "中文->韩文", Toast.LENGTH_SHORT)
						.show();
				trabslate_result.setText("译文：" + "\n");
				translateType = 6;
				break;
			default:
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			Toast.makeText(getActivity(), "onNothingSelected",
					Toast.LENGTH_SHORT).show();
		};
	}

	class TranslateListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (translateType) {
			case 0:
				if (BooleanUtils.isNull(translate_content.getText().toString())) {
					Toast.makeText(getActivity(), "请您输入您要翻译的内容",
							Toast.LENGTH_SHORT).show();
				} else {
					client.translate(translate_content.getText().toString(),
							Language.AUTO, Language.CHINESE,
							new ITransResultCallback() {

								@Override
								public void onResult(TransResult result) {

									JSONObject mJsonObject = JSONArray
											.parseObject(result.toJSONString());
									String result_dest = mJsonObject
											.getString("trans_result");
									trabslate_result.setText("译文：" + "\n"
											+ result_dest);
									System.out.println("trans_result:"
											+ result_dest);
								}
							});
					client.dict(translate_content.getText().toString(),
							Language.AUTO, Language.CHINESE, new IDictResultCallback() {
								
								@Override
								public void onResult(DictResult dictResult) {
									JSONObject mJsonObject = JSONArray
											.parseObject(dictResult.toJSONString());
									String result_dest = mJsonObject
											.getString("trans_result");
								
									
//									trabslate_result.setText("译文：" + "\n"
//											+ result_dest);
									System.out.println("test trans_result:"
											+ dictResult.toJSONString().toString());
				//{"data":{"word_name":"我",
						//"symbols":[{"parts":[{"means":["I","me","myself"]}],"ph_zh":"wǒ"}]},"to":"en","from":"zh","errno":0}
					
									
								}
							});
				}

				break;
			case 1:
				if (BooleanUtils.isNull(translate_content.getText().toString())) {
					Toast.makeText(getActivity(), "请您输入您要翻译的内容",
							Toast.LENGTH_SHORT).show();
				} else {
					client.translate(translate_content.getText().toString(),
							Language.ENGLISH, Language.CHINESE,
							new ITransResultCallback() {

								@Override
								public void onResult(TransResult result) {

									JSONObject mJsonObject = JSONArray
											.parseObject(result.toJSONString());
									String result_dest = mJsonObject
											.getString("trans_result");
									trabslate_result.setText("译文：" + "\n"
											+ result_dest);
									System.out.println("trans_result:"
											+ result_dest);
								}
							});
				}

				break;
			case 2:
				if (LangueUtils.isChinese(translate_content.getText()
						.toString())) {
					client.translate(translate_content.getText().toString(),
							Language.CHINESE, Language.ENGLISH,
							new ITransResultCallback() {

								@Override
								public void onResult(TransResult result) {

									JSONObject mJsonObject = JSONArray
											.parseObject(result.toJSONString());
									String result_dest = mJsonObject
											.getString("trans_result");
									trabslate_result.setText("译文：" + "\n"
											+ result_dest);
									System.out.println("trans_result:"
											+ result_dest);
								}
							});
				} else {
					Toast.makeText(getActivity(), "您输入的不是中文",
							Toast.LENGTH_SHORT).show();
				}

				break;
			case 3:
				if (LangueUtils.isJapanese(translate_content.getText()
						.toString())) {
					client.translate(translate_content.getText().toString(),
							Language.JAPANESE, Language.CHINESE,
							new ITransResultCallback() {

								@Override
								public void onResult(TransResult result) {

									JSONObject mJsonObject = JSONArray
											.parseObject(result.toJSONString());
									String result_dest = mJsonObject
											.getString("trans_result");
									trabslate_result.setText("译文：" + "\n"
											+ result_dest);
									System.out.println("trans_result:"
											+ result_dest);
								}
							});
				} else {
					Toast.makeText(getActivity(), "您输入的不是日语",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case 4:
				if (LangueUtils.isChinese(translate_content.getText()
						.toString())) {
					client.translate(translate_content.getText().toString(),
							Language.CHINESE, Language.JAPANESE,
							new ITransResultCallback() {

								@Override
								public void onResult(TransResult result) {

									JSONObject mJsonObject = JSONArray
											.parseObject(result.toJSONString());
									String result_dest = mJsonObject
											.getString("trans_result");
									trabslate_result.setText("译文：" + "\n"
											+ result_dest);
									System.out.println("trans_result:"
											+ result_dest);
								}
							});
				} else {
					Toast.makeText(getActivity(), "您输入的不是中文",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case 5:
				if (LangueUtils
						.isKorean(translate_content.getText().toString())) {
					client.translate(translate_content.getText().toString(),
							Language.KOREAN, Language.CHINESE,
							new ITransResultCallback() {

								@Override
								public void onResult(TransResult result) {

									JSONObject mJsonObject = JSONArray
											.parseObject(result.toJSONString());
									String result_dest = mJsonObject
											.getString("trans_result");
									trabslate_result.setText("译文：" + "\n"
											+ result_dest);
									System.out.println("trans_result:"
											+ result_dest);
								}
							});
				} else {
					Toast.makeText(getActivity(), "您输入的不是韩语",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case 6:
				if (LangueUtils.isChinese(translate_content.getText()
						.toString())) {
					client.translate(translate_content.getText().toString(),
							Language.CHINESE, Language.KOREAN,
							new ITransResultCallback() {

								@Override
								public void onResult(TransResult result) {

									JSONObject mJsonObject = JSONArray
											.parseObject(result.toJSONString());
									String result_dest = mJsonObject
											.getString("trans_result");
									trabslate_result.setText("译文：" + "\n"
											+ result_dest);
									System.out.println("trans_result:"
											+ result_dest);
								}
							});
				} else {
					Toast.makeText(getActivity(), "您输入的不是中文",
							Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}
		}
	}
}
