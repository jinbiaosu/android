package android.vein.utils.libutils.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by vein on 16/8/25.
 */
public class JActivityUtils {
    /**
     * 应用是否再前台
     * @param context
     * @return
     */
    public static  boolean isForeground(Context context){
        ActivityManager activityManager=(ActivityManager)context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> taskInfos=activityManager.getRunningAppProcesses();
        if (JListUtils.isEmpty(taskInfos)){
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo info : taskInfos){
            if (info.processName.equals(context.getPackageName())&&(info.importance== ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)){
                return  true;
            }
        }
        return  false;
    }

    /**
     * 启动应用，可用于应用闪退后重启
     * @param context
     */
    public static void launchApp(Context context){
        try {
            Intent intent = new Intent();
            PackageManager packageManager = context.getPackageManager();
            intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
