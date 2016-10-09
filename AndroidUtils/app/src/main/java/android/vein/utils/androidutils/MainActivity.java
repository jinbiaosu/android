package android.vein.utils.androidutils;

import android.database.Observable;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.SparseArray;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;


public class MainActivity extends AppCompatActivity {
    private VerticalTabWidget verticalTabWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verticalTabWidget = (VerticalTabWidget) findViewById(R.id.bottom_view);

        List<TabViewModel> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            TabViewModel tabViewModel = new TabViewModel();
            tabViewModel.setTabText("hellow" + i);
            list.add(tabViewModel);
        }
        verticalTabWidget.initViews(list);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        //释放UI资源
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
