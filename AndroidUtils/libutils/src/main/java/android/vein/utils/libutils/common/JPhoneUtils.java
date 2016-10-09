package android.vein.utils.libutils.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by vein on 16/8/25.
 */
public class JPhoneUtils {
    /**
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (imei == null)
            imei = "";
        return imei;
    }

    /**
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        if (imsi == null)
            imsi = "";
        return imsi;
    }

    /**
     * @param context
     * @return
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getNetworkOperatorName();
    }

    /**
     * @param activity
     * @return
     */
    public static String getNetworkOperatorName(Activity activity) {
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Activity.TELEPHONY_SERVICE);
        return tm.getNetworkOperatorName();
    }

    /**
     * @return
     */
    public static String getPhoneType() {
        String type = Build.MODEL;
        if (type != null) {
            type = type.replace(" ", "");
        }
        return type.trim();
    }

    /**
     * 获取设备生产商
     *
     * @return
     */
    public static String getDeviceMake() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备模型
     *
     * @param context applicationContext
     * @return pad or phone
     */
    public static String getDeviceModel(Context context) {
        boolean b = (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (b) {
            return "pad";
        } else {
            return "phone";
        }
    }

    /**
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * @return
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * @return
     */
    public static String getType() {
        return Build.TYPE;
    }

    /**
     * @return
     */
    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * @return
     */
    public static String getSDKVersion() {
        return Build.VERSION.SDK;
    }

    /**
     * @return
     */
    public static int getAndroidSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * @param context
     * @return
     */
    public static String getResolution(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm.widthPixels + "x" + dm.heightPixels;
    }

    /**
     * @param context
     * @return
     */
    public static int getDisplayWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * @param context
     * @return
     */
    public static int getDisplayHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    /**
     * @param context
     * @return
     */
    public static float getDisplayDensity(Context context) {
        return getDisplayMetrics(context).density;
    }

    /**
     * @return
     */
    public static float getDisplayDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * @return
     */
    public static String getPhoneLanguage() {
        String language = Locale.getDefault().getLanguage();
        if (language == null) {
            language = "";
        }
        return language;
    }

    /**
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info == null) {
            return "";
        }
        if (TextUtils.isEmpty(info.getMacAddress())) {
            return "";
        }
        return info.getMacAddress();

    }

    /**
     * @param context
     * @return
     */
    public static int getCacheSize(Context context) {
        return 1024 * 1024 * ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 4;
    }

    /**
     * @param context
     * @param unit
     * @param size
     * @return
     */
    public static int getRawSize(Context context, int unit, float size) {
        Resources resources;
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        return (int) TypedValue.applyDimension(unit, size, resources.getDisplayMetrics());
    }

    /**
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, getDisplayMetrics(context));
    }

    /**
     * @param context
     * @param dipValue
     * @return
     */
    public static float dip2pxFloat(Context context, float dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, getDisplayMetrics(context));
    }

    /**
     * @param context
     * @param px
     * @return
     */
    public static int px2dip(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    /**
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * @param spValue
     * @return
     */
    public static float sp2pxFloat(float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * @param activity
     * @return
     */
    public static int getDisplayDpi(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }

    /**
     * 获得屏幕宽高
     *
     * @param context
     * @return
     */
    public static Map<String, Integer> getDisplay(Context context) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        if (display != null) {
            map.put("DeviceHeight", display.getHeight());
            map.put("DeviceWidth", display.getWidth());
            return map;
        } else {
            return null;
        }
    }

}
