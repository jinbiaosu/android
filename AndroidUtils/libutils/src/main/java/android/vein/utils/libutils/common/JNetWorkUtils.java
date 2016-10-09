package android.vein.utils.libutils.common;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * Created by vein on 16/8/25.
 */
public class JNetWorkUtils {
    /**
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String typeName = info.getTypeName().toLowerCase(); // WIFI/MOBILE
            if (typeName.equals("wifi")) {
                typeName = "wifi";
            } else {
                try {
                    typeName = info.getExtraInfo().toLowerCase(); // 3gnet/3gwap/uninet/uniwap/cmnet/cmwap/ctnet/ctwap
                } catch (Exception e) {
                    return "";
                }
            }
            return typeName;
        } else {
            return "wifi not available";
        }
    }

    /**
     * @param context
     * @return
     */
    public static WifiInfo getWifiStatus(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo;
    }

    /**
     * @param context
     * @return
     */
    public static String getAccessPointType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netWorkInfo = manager.getActiveNetworkInfo();
        if (netWorkInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
            return netWorkInfo.getExtraInfo();
        } else {
            return null;
        }
    }

    /**
     * @param context
     * @return
     */
    public static boolean isMobileType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null)
            return false;
        NetworkInfo netWorkInfo = manager.getActiveNetworkInfo();
        if (netWorkInfo == null)
            return false;
        if (netWorkInfo.getTypeName().equalsIgnoreCase("mobile")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param context
     * @return
     */
    public static boolean isCmwap(Context context) {
        if (!isConnectChinaMobile(context) || !isMobileType(context))
            return false;

        String currentApnProxy = getCurrentApnProxy(context);
        if (currentApnProxy == null) {
            return false;
        }
        if (currentApnProxy.equals("10.0.0.172") || currentApnProxy.equals("010.000.000.172")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param context
     * @return
     */
    public static boolean isUniwap(Context context) {
        if (!isConnectChinaUnicom(context) || !isMobileType(context))
            return false;

        String currentApnProxy = getCurrentApnProxy(context);
        if (currentApnProxy == null) {
            return false;
        }
        if (currentApnProxy.equals("10.0.0.172") || currentApnProxy.equals("010.000.000.172")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param context
     * @return
     */
    public static String getCurrentApnProxy(Context context) {
        Cursor c = null;
        try {
            Uri uri = Uri.parse("content://telephony/carriers/preferapn");
            c = context.getContentResolver().query(uri, null, null, null, null);
            if (c != null && c.moveToFirst()) {
                return c.getString(c.getColumnIndex("proxy"));
            }
        } finally {
            if (c != null)
                c.close();
        }
        return null;
    }

    /**
     * @param apnId
     * @param context
     * @return
     */
    public static String getProxyIp(String apnId, Context context) {
        if (apnId == null)
            return null;
        Cursor c = null;
        try {
            Uri uri = Uri.parse("content://telephony/carriers");
            c = context.getContentResolver().query(uri, null, null, null, null);
            while (c != null && c.moveToNext()) {
                // APN id
                String id = c.getString(c.getColumnIndex("_id"));
                if (apnId.trim().equals(id)) {
                    return c.getString(c.getColumnIndex("proxy"));
                }
            }
        } finally {
            if (c != null)
                c.close();
        }
        return null;
    }

    /**
     * @param context
     * @return
     */
    public static boolean isConnectChinaMobile(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telManager.getSimOperator();
        if (operator != null) {
            if (operator.startsWith("46000") || operator.startsWith("46002")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @param context
     * @return
     */
    public static boolean isConnectChinaUnicom(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telManager.getSimOperator();
        if (operator != null) {
            if (operator.startsWith("46001")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @param context
     * @return
     */
    public static boolean isConnectChinaTelecom(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telManager.getSimOperator();
        if (operator != null) {
            if (operator.startsWith("46003")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @param context
     * @return
     */
    public static String getNetWorkName(Context context) {
        String netWorkType = getNetworkType(context);
        netWorkType = netWorkType.toLowerCase();
        return netWorkType;
    }

    public static String getServiceName(Context context) {
        String netWorkType = getNetworkType(context);
        if (netWorkType.equals("wifi")) {
            netWorkType = "wifi";
        } else {
            if (isConnectChinaMobile(context)) {// 移动
                netWorkType = "mobile";
            } else if (isConnectChinaUnicom(context)) {// 联通
                netWorkType = "unicom";
            } else if (isConnectChinaTelecom(context)) {// 电信
                netWorkType = "telecom";
            } else {
                netWorkType = "";
            }
        }
        return netWorkType;
    }

}
