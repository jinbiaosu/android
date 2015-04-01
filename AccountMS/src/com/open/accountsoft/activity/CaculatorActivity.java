/*Programming by zlzsam*/
package com.open.accountsoft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.open.accountsoft.utils.SPUtils;
import com.vein.accountsoft.activity.R;

public class CaculatorActivity extends Activity {

	View vi;
	String str = "", secstr = "";
	EditText primaryET;
	/* EditText secondaryET; */
	int flag = 0, c = 0;
	double b = 0.0, g = 0.0, f = 0.0;

	public void substr() {
		int a = str.indexOf(".");
		int b = str.indexOf("E");
		String tempstr = str;
		if (a == -1) {
			if (str.length() > 12)
				str = str.substring(0, 12);
		}
		if (a > 0) {
			if (b == -1) {
				if (str.length() > 12)
					str = str.substring(0, 12);
			}
			if (b > 0) {
				tempstr = str.substring(b);
				if (str.length() > 12) {
					int perStrLen = 12 - tempstr.length();
					str = str.substring(0, perStrLen) + tempstr;
				}
			}
		}

	}

	public double calc() {
		switch (c) {
		case 0:
			f = g;
			break;
		case 1:
			f = b + g;
			break;
		case 2:
			f = b - g;
			break;
		case 3:
			f = b * g;
			break;
		case 4:
			f = b / g;
			break;
		}

		b = f;
		c = 0;

		return f;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);

		final Button number[] = new Button[10];
		final Button mark[] = new Button[13];

		mark[0] = (Button) findViewById(R.id.markone);
		mark[1] = (Button) findViewById(R.id.marktwo);
		mark[2] = (Button) findViewById(R.id.markthree);
		mark[3] = (Button) findViewById(R.id.markfour);
		mark[4] = (Button) findViewById(R.id.markfive);
		mark[5] = (Button) findViewById(R.id.marksix);
		mark[6] = (Button) findViewById(R.id.markseven);
		mark[7] = (Button) findViewById(R.id.markeight);
		mark[8] = (Button) findViewById(R.id.marknine);
		mark[9] = (Button) findViewById(R.id.markten);
		mark[10] = (Button) findViewById(R.id.markeleven);
		mark[11] = (Button) findViewById(R.id.marktwelve);
		mark[12] = (Button) findViewById(R.id.markthreeteen);

		// ��ȡ���ְ�ť
		number[0] = (Button) findViewById(R.id.btnzero);
		number[1] = (Button) findViewById(R.id.btnone);
		number[2] = (Button) findViewById(R.id.btntwo);
		number[3] = (Button) findViewById(R.id.btnthree);
		number[4] = (Button) findViewById(R.id.btnfour);
		number[5] = (Button) findViewById(R.id.btnfive);
		number[6] = (Button) findViewById(R.id.btnsix);
		number[7] = (Button) findViewById(R.id.btnseven);
		number[8] = (Button) findViewById(R.id.btneight);
		number[9] = (Button) findViewById(R.id.btnnine);

		// ��ȡ����ʾ��
		primaryET = (EditText) findViewById(R.id.edittext2_show);

		/*
		 * // ��ȡ����ʾ�� secondaryET = (EditText)
		 * findViewById(R.id.edittext1_show);
		 */

		primaryET.setText(str);
		/* secondaryET.setText(secstr); */

		// ��C��ť���¼��Ĵ���
		mark[0].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				b = 0.0;
				c = 0;
				g = 0.0;
				str = "";
				primaryET.setText(str);
				secstr = "";
				/* secondaryET.setText(secstr); */
			}
		});

		// ��CE��ť���¼��Ĵ���
		mark[1].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str = "";
				primaryET.setText(str);
				vi = v;
			}
		});

		// ��ƽ�����¼��Ĵ���
		mark[2].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (str != "") {
					double temp = Double.parseDouble(str);
					str = "" + temp * temp;
					substr();
					primaryET.setText(str);
				}
			}
		});

		// �Ը�ŵ��¼��Ĵ���
		mark[3].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (str != "") {
					double temp = Double.parseDouble(str);
					str = Math.sqrt(temp) + "";
					substr();
					primaryET.setText(str);
				}
			}
		});
		// ��backspace���¼��Ĵ���
		mark[4].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (str != "") {
					if (str.length() == 1) {
						str = "";
						primaryET.setText(str);
					} else {
						str = str.substring(0, str.length() - 1);
						primaryET.setText(str);
					}

				}

				else {
					primaryET.setText(str);
				}
			}
		});

		// ����ŵ��¼��Ĵ���
		mark[5].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (vi != mark[11] && str != "") {
					char ch = str.charAt(0);
					if (ch == '-')
						str = str.replace("-", "");
					else
						str = "-" + str;
					primaryET.setText(str);
				}
			}
		});

		// ����1/x�¼��Ĵ���
		mark[6].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (str != "") {
					Double d = Double.parseDouble(str);
					Double s = 1.0 / d;
					str = "" + s.toString();
					substr();
					primaryET.setText(str);
				} else {
					primaryET.setText("0");
				}
			}
		});

		// �Գ�ŵ��¼��Ĵ���
		mark[7].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (str != "") {
					if (vi == mark[7] || vi == mark[8] || vi == mark[9]
							|| vi == mark[10]) {
						c = 4;
					} else {
						g = Double.parseDouble(str);
						calc();
						str = "" + f;
						substr();
						primaryET.setText(str);
						c = 4;
						flag = 1;
						vi = v;
					}
				}
			}
		});

		// �Գ˺ŵ��¼��Ĵ���
		mark[8].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (str != "") {
					if (vi == mark[7] || vi == mark[8] || vi == mark[9]
							|| vi == mark[10]) {
						c = 3;
					} else {
						g = Double.parseDouble(str);
						calc();
						str = "" + f;
						substr();
						primaryET.setText(str);
						c = 3;
						flag = 1;
						vi = v;
					}
				}
			}
		});

		// �Լ��ŵ��¼��Ĵ���
		mark[9].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (str != "") {
					if (vi == mark[7] || vi == mark[8] || vi == mark[9]
							|| vi == mark[10]) {
						c = 2;
					} else {
						g = Double.parseDouble(str);
						calc();
						str = "" + f;
						substr();
						primaryET.setText(str);
						c = 2;
						flag = 1;
						vi = v;
					}
				}
			}
		});

		// �ԼӺŵ��¼��Ĵ���
		mark[10].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (str != "") {
					if (vi == mark[7] || vi == mark[8] || vi == mark[9]
							|| vi == mark[10]) {
						c = 1;
					} else {
						g = Double.parseDouble(str);
						calc();
						str = "" + f;
						substr();
						primaryET.setText(str);
						c = 1;
						flag = 1;
						vi = v;
					}
				}
			}
		});

		// ��С���Ĵ���
		mark[11].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (str == "") {
					str += ".";
					primaryET.setText(str);
				} else {
					char ch[];
					int x = 0;
					ch = str.toCharArray();
					for (int i = 0; i < ch.length; i++)
						if (ch[i] == '.')
							x++;
					if (x == 0) {
						str += ".";
						primaryET.setText(str);
					}

				}
			}
		});

		mark[12].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (str != "" && vi != mark[7] && vi != mark[8]
						&& vi != mark[9] && vi != mark[10]) {
					g = Double.parseDouble(str);
					calc();
					str = "" + f;
					substr();
					primaryET.setText(str);
					flag = 1;
					vi = v;

				}
			}
		});
		// �趨���ְ���
		number[0].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 1) {
					str = "";
					str += 0;
					primaryET.setText(str);
					flag = 0;
				} else {
					char ch1[];
					ch1 = str.toCharArray();
					if (!(ch1.length == 1 && ch1[0] == '0')) {
						str += 0;
						primaryET.setText(str);
					}

				}
				vi = v;
			}
		});

		number[1].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 1) {
					str = "";
					str += 1;
					primaryET.setText(str);
					flag = 0;
				} else {
					str += 1;
					primaryET.setText(str);
				}
				vi = v;
			}
		});

		number[2].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 1) {
					str = "";
					str += 2;
					primaryET.setText(str);
					flag = 0;
				} else {
					str += 2;
					primaryET.setText(str);
				}
				vi = v;
			}
		});

		number[3].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 1) {
					str = "";
					str += 3;
					primaryET.setText(str);
					flag = 0;
				} else {
					str += 3;
					primaryET.setText(str);
				}
				vi = v;
			}
		});

		number[4].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 1) {
					str = "";
					str += 4;
					primaryET.setText(str);
					flag = 0;
				} else {
					str += 4;
					primaryET.setText(str);
				}
				vi = v;
			}
		});

		number[5].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 1) {
					str = "";
					str += 5;
					primaryET.setText(str);
					flag = 0;
				} else {
					str += 5;
					primaryET.setText(str);
				}
				vi = v;
			}
		});

		number[6].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 1) {
					str = "";
					str += 6;
					primaryET.setText(str);
					flag = 0;
				} else {
					str += 6;
					primaryET.setText(str);
				}
				vi = v;
			}
		});

		number[7].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 1) {
					str = "";
					str += 7;
					primaryET.setText(str);
					flag = 0;
				} else {
					str += 7;
					primaryET.setText(str);
				}
				vi = v;
			}
		});

		number[8].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 1) {
					str = "";
					str += 8;
					primaryET.setText(str);
					flag = 0;
				} else {
					str += 8;
					primaryET.setText(str);
				}
				vi = v;
			}
		});

		number[9].setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == 1) {
					str = "";
					str += 9;
					primaryET.setText(str);
					flag = 0;
				} else {
					str += 9;
					primaryET.setText(str);
				}
				vi = v;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		SPUtils.put(this, "CaculatorReturn", str);
	}

	@Override
	protected void onStart() {
		super.onStart();
		str = (String) SPUtils.get(this, "CaculatorReturn"," ");
		primaryET.setText(str);
	}
}
