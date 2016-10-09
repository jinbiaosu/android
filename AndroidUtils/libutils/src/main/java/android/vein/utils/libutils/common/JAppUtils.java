package android.vein.utils.libutils.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

public class JAppUtils {
    private static  final  int ERROR_NUM=-1;
    private static  final  String ERROR_STR="";
    /**
     *
     * @param context  ApplicationContext
     * @return
     */
    public static String getAppName(Context context) {
        try {
            return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  ERROR_STR;
    }

    /**
     *
     * @param context  ApplicationContext
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ERROR_STR;
    }

    /**
     *
     * @param context  ApplicationContext
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_NUM;
    }

    /**
     *
     * @param context ApplicationContext
     * @return
     */
    public static String getPackageName(Context context) {
        try{
            return context.getPackageName();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  ERROR_STR;
    }

    /**
     *
     * @param context  ApplicationContext
     * @return
     */
    public static Drawable getAppIcon(Context context) {
        try {
            return context.getApplicationInfo().loadIcon(context.getPackageManager());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param context  ApplicationContext
     * @param processName
     * @return
     */
    public static int getPid(Context context, String processName) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcessList) {
            if (processName.equals(appProcess.processName)) {
                return appProcess.pid;
            }
        }
        return ERROR_NUM;
    }

    /**
     *
     * @param context
     * @return
     */
    public static String getSignInfo(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            Signature signature = signatures[0];
            String signMd5 = parseSignature(signature.toByteArray());
            return  signMd5;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_STR;
    }

    private static String parseSignature(byte[] signature) {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(signature));
            String pubKey = cert.getPublicKey().toString();
            String signNumber = cert.getSerialNumber().toString();
            return signNumber;
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return ERROR_STR;
    }

    /**
     *
     * @param context
     * @param metaKey
     * @return
     */
    public static String getMetaValue(Context context, String metaKey) {
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = null;
            String apiKey = null;
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
            if (!TextUtils.isEmpty(apiKey)){
                return  apiKey;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_STR;
    }

}
