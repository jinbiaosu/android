package bj.vein.com.mylearning.myutils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by sjbyyan on 2015/2/16.
 */
public class MyDevicesUtils {
    private static String sAppName = "";

    public static File getAppCacheDir(Context context, String subName) {
        if (!sdAvailible()) {
            return null;
        }
        File sd = Environment.getExternalStorageDirectory();
        File dir = new File(sd, getAppName(context));
        File sub = new File(dir, subName);
        sub.mkdirs();
        return sub;
    }

    public static boolean sdAvailible() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    public static String encrypt(String str) {
        // TODO: encrypt data.
        return str;
    }

    public static String buildSystemInfo(Context context) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n");
        buffer.append("#-------system info-------");
        buffer.append("\n");
        buffer.append("version-name:");
        buffer.append(MyDevicesUtils.getVersionName(context));
        buffer.append("\n");
        buffer.append("version-code:");
        buffer.append(MyDevicesUtils.getVersionCode(context));
        buffer.append("\n");
        buffer.append("system-version:");
        buffer.append(MyDevicesUtils.getSystemVersion(context));
        buffer.append("\n");
        buffer.append("model:");
        buffer.append(MyDevicesUtils.getModel(context));
        buffer.append("\n");
        buffer.append("density:");
        buffer.append(MyDevicesUtils.getDensity(context));
        buffer.append("\n");
        buffer.append("imei:");
        buffer.append(MyDevicesUtils.getIMEI(context));
        buffer.append("\n");
        buffer.append("screen-height:");
        buffer.append(MyDevicesUtils.getScreenHeight(context));
        buffer.append("\n");
        buffer.append("screen-width:");
        buffer.append(MyDevicesUtils.getScreenWidth(context));
        buffer.append("\n");
        buffer.append("unique-code:");
        buffer.append(MyDevicesUtils.getUniqueCode(context));
        buffer.append("\n");
        buffer.append("mobile:");
        buffer.append(MyDevicesUtils.getMobile(context));
        buffer.append("\n");
        buffer.append("imsi:");
        buffer.append(MyDevicesUtils.getProvider(context));
        buffer.append("\n");
        buffer.append("isWifi:");
        buffer.append(MyDevicesUtils.isWifi(context));
        buffer.append("\n");
        buffer.append("ipAddress:");
        buffer.append(MyDevicesUtils.getIpAddress(context));
        buffer.append("\n");
        buffer.append("TotalMemory:");
        buffer.append(MyDevicesUtils.getTotalMemory());

        return buffer.toString();
    }

    public static String getUniqueCode(Context context) {
        if (context == null)
            return null;
        String imei = getIMEI(context);
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mUniqueCode = imei + "_" + info.getMacAddress();
        return mUniqueCode;
    }

    public static boolean isWifi(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static String getMobile(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    public static String getProvider(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }

    public static final String getIMEI(final Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static String getSystemVersion(Context context) {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getModel(Context context) {
        return android.os.Build.MODEL != null ? android.os.Build.MODEL.replace(
                " ", "") : "unknown";
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo pinfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            return pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return "";
    }

    public static String getAppName(Context context) {
        if (TextUtils.isEmpty(sAppName)) {
            sAppName = "com_forlong401_log";
            try {
                PackageInfo pinfo = context.getPackageManager().getPackageInfo(
                        context.getPackageName(),
                        PackageManager.GET_CONFIGURATIONS);
                String packageName = pinfo.packageName;
                if (!TextUtils.isEmpty(packageName)) {
                    sAppName = packageName.replaceAll("\\.", "_");
                }
            } catch (PackageManager.NameNotFoundException e) {
            }
        }

        return sAppName;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo pinfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            return pinfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return 1;
    }

    public static String getIpAddress(Context mContext) {
        String ipAddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipAddress = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            return null;
        }
        return ipAddress;
    }

    /**
     * 获取设备内存大小值
     *
     * @return 内存大小,单位MB
     */
    public static long getTotalMemory() {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            if (str2 != null) {
                arrayOfString = str2.split("\\s+");
                initial_memory = Integer.valueOf(arrayOfString[1]).intValue() / 1024;
            }
            localBufferedReader.close();
            return initial_memory;
        } catch (IOException e) {
            return -1;
        }
    }
}
