package android.vein.utils.androidutils;

import android.app.Application;

/**
 * Created by vein on 16/8/25.
 */
public class UtilsApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
    }
    public String appName(){
        return getApplicationContext().getPackageName();
    }
}
