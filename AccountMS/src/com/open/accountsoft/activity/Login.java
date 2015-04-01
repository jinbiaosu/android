package com.open.accountsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.open.accountsoft.dao.PwdDAO;
import com.open.accountsoft.utils.AccountConstant;
import com.vein.accountsoft.activity.R;
import com.xiaomi.account.openauth.XiaomiOAuthorize.OnOAuthInterface;

public class Login extends Activity implements OnOAuthInterface {
	EditText txtlogin;// 创建EditText对象
	TextView milogintv;
	Button btnlogin, btnclose, miloginbtn;// 创建两个Button对象

	private static String USER_PROFILE_PATH = AccountConstant.USER_PROFILE_PATH;

	private static String USER_RELATION_PATH = AccountConstant.USER_RELATION_PATH;

	public static int REQUESTCODE_TOKEN = AccountConstant.REQUESTCODE_TOKEN;

	public static int REQUESTCODE_CODE = AccountConstant.REQUESTCODE_CODE;

	Button mGetToken;

	Button mGetCode;

	Button mProfile;

	Button mRelation;

	String userId;

	Long clientId = AccountConstant.CLIENT_ID;

	String redirectUri = AccountConstant.REDIRECT_URL;

	String clientSecret = AccountConstant.APP_SECRET;

	String code;

	String state;

	String tokenType;

	String macKey;

	String macAlgorithm;

	String expiresIn;

	String scope;

	private String accessToken;

	String refreshToken;

	String error;

	String errorDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);// 设置布局文件

		txtlogin = (EditText) findViewById(R.id.txtLogin);// 获取密码文本框
		btnlogin = (Button) findViewById(R.id.btnLogin);// 获取登录按钮
		btnclose = (Button) findViewById(R.id.btnClose);// 获取取消按钮
		miloginbtn = (Button) findViewById(R.id.miauth_log);
		milogintv = (TextView) findViewById(R.id.miauth_tv);

		milogintv.setVisibility(View.GONE);
		miloginbtn.setVisibility(View.GONE);

		// miloginbtn.setOnClickListener(new MiLonginListener());

		btnlogin.setOnClickListener(new OnClickListener() {// 为登录按钮设置监听事件
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Login.this, MainActivity.class);// 创建Intent对象
				PwdDAO pwdDAO = new PwdDAO(Login.this);// 创建PwdDAO对象
				// 判断是否有密码及是否输入了密码
				if ((pwdDAO.getCount() == 0 || pwdDAO.find().getPassword()
						.isEmpty())) {
					if (txtlogin.getText().toString().isEmpty()) {
						startActivity(intent);// 启动主Activity
					} else {
						Toast.makeText(Login.this, "您还没有设置过密码",
								Toast.LENGTH_SHORT).show();
					}

				} else {
					// 判断输入的密码是否与数据库中的密码一致
					if ((pwdDAO.find().getPassword()).equals(txtlogin.getText()
							.toString())) {
						startActivity(intent);// 启动主Activity
					} else {
						// 弹出信息提示
						Toast.makeText(Login.this, "请输入正确的密码！",
								Toast.LENGTH_SHORT).show();
					}
				}
				txtlogin.setText("");// 清空密码文本框
			}
		});

		btnclose.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();// 退出当前程序
			}
		});
	}

	class MiLonginListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			// Bundle options = new Bundle();
			// options.putString(AuthConstants.EXTRA_SCOPE, String.valueOf(1));
			//
			// XiaomiOAuthorize.startGetAccessToken(Login.this, clientId,
			// redirectUri, options, // options
			// REQUESTCODE_TOKEN);
		}

	}

	@Override
	public void onGetAccessTokenDirectly(Bundle bundle) {
		processAuthResult(bundle);
	}

	private void processAuthResult(Bundle bundle) {
		accessToken = bundle.getString("access_token");
		expiresIn = bundle.getString("expires_in");
		scope = bundle.getString("scope");
		state = bundle.getString("state");
		tokenType = bundle.getString("token_type");
		macKey = bundle.getString("mac_key");
		macAlgorithm = bundle.getString("mac_algorithm");
		// mContentView.setText("accessToken=" + accessToken + ",expiresIn=" +
		// expiresIn
		// + ",scope=" + scope
		// + ",state=" + state + ",tokenType=" + tokenType + ",macKey=" + macKey
		// + ",macAlogorithm="
		// + macAlgorithm);
	}
}
