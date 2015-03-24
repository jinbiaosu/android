package bj.vein.com.mylearning.lesson2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import bj.vein.com.mylearning.R;

/**
 * Created by sjbyyan on 2015/2/15.
 */
public class MyClockActivity extends Activity{
    private TextView lesson2_tv;
    private static  final int ToastMsg=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson2);
        init();
        new Thread(new ClockThread()).start();
    }
    public void init(){
        lesson2_tv=(TextView)findViewById(R.id.lesson2_tv);
    }
private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case ToastMsg:
                lesson2_tv.setText("当前系统时间是:"+msg.obj.toString());
                break;
            default:
                break;
        }
    }
};
    private class ClockThread implements Runnable{
        @Override
        public void run() {
            while (true)
            {
                Message message=MyClockActivity.this.handler.obtainMessage(ToastMsg,
                        new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new java.util.Date()));
                MyClockActivity.this.handler.sendMessage(message);
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }
    }
}
