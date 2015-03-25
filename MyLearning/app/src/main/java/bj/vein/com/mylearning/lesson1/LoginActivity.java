package bj.vein.com.mylearning.lesson1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import bj.vein.com.mylearning.R;

/**
 * Created by sjbyyan on 2015/2/11.
 */
public class LoginActivity extends Activity{
    String TAG=LoginActivity.class.getName();
    private ImageView verifyImg;
    private EditText verify_et;
    private Button login_btn;
    private Button reset_btn;
    private EditText username;
    private EditText password;
    String getCode=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson1);
        initView();
        login_btn.setOnClickListener(new LoginListener());
        reset_btn.setOnClickListener(new ResetListener());
        verifyImg.setOnClickListener(new ImageViewChangeListener());
    }
    private void initView(){
        username=(EditText)findViewById(R.id.lesson1_edit_username);
        password=(EditText)findViewById(R.id.lesson1_edit_pwd);

        login_btn=(Button)findViewById(R.id.lesson1_btn_login);
        reset_btn=(Button)findViewById(R.id.lesson1_btn_reset);

        verifyImg=(ImageView)findViewById(R.id.verify_img);
        verifyImg.setImageBitmap(VerifyCode.getInstance().getBitmap());
        verify_et=(EditText)findViewById(R.id.lesson1_edit_verify);
    }
    class LoginListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String usernameString=username.getText().toString();
            String passwordString=password.getText().toString();

            //get verify that user input
            String checkEdit=verify_et.getText().toString();
            //get verify that system's generation
            getCode=VerifyCode.getInstance().getCode();
            Log.d(TAG,"getCode : "+getCode);

            if("vein".equals(usernameString)&&"111111".equals(passwordString)){
                if(checkEdit.equals(getCode)){
                    Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"您输入的验证码不正确，请重新输入",Toast.LENGTH_SHORT).show();
                    verifyImg.setImageBitmap(VerifyCode.getInstance().getBitmap());
                }
            }else{
                Toast.makeText(getApplicationContext(),"您输入的账号或者密码不正确，请重新输入",Toast.LENGTH_SHORT).show();
            }

        }
    }
    class ResetListener implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            username.setText("");
            password.setText("");
            verify_et.setText("");
//            verifyImg.setImageBitmap(VerifyCode.getInstance().getBitmap());
        }
    }
    class ImageViewChangeListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            verifyImg.setImageBitmap(VerifyCode.getInstance().getBitmap());
        }
    }
}
