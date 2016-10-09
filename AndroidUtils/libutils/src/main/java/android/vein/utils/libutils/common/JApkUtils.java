package android.vein.utils.libutils.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;
import java.util.List;

/**
 * Created by vein on 16/8/25.
 */
public class JApkUtils {
    /**
     * install apk on the sdCard
     * @param context
     * @param apkSDPath
     */
    public static void installApk(Context context, String apkSDPath) {
        File file = new File(apkSDPath);
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    /**
     * 根据包名启动应用程序
     * @param context
     * @param packageName
     */
    public static void launchApk(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            context.startActivity(intent);
        }
    }
    /**
     *
     * @param context
     * @param path --- apk path
     * @return
     */
    public static String getPackageName(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            return appInfo.packageName;
        } else {
            return null;
        }
    }
    /**
     * 根据包名判断是否安装过该应用
     * @param packageName
     * @param context
     * @return
     */
    public static boolean isInstallApk(String packageName, Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (int i = 0, size = packageInfoList.size(); i < size; i++) {
            PackageInfo packageInfo = (PackageInfo) packageInfoList.get(i);
            if (packageName.equals(packageInfo.packageName)) {
                return true;
            }

        }
        return false;
    }

    public static int sdkVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

}
