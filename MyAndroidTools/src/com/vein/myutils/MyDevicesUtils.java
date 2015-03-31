package com.vein.myutils;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class MyDevicesUtils {

	public static final String TAG = MyDevicesUtils.class.getName();
	public static long lastClickTime;

	private static String sAppName = "";

	/**
	 * judge if is fast double click
	 * 
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	public static String getAppName(Context context, String appPackageName) {
		PackageManager pm = context.getPackageManager();
		String appName = null;
		try {
			appName = pm.getApplicationInfo(appPackageName, 0).loadLabel(pm)
					.toString();
			return appName;
		} catch (NameNotFoundException e) {
			return appName;
		}
	}

	public static String getVersionName(Context context, String packageName) {
		PackageManager pm = context.getPackageManager();
		int flags = 0;
		PackageInfo packageInfo;
		String versionName = null;
		try {
			packageInfo = pm.getPackageInfo(packageName, flags);
			versionName = packageInfo.versionName;
			int versioncode = packageInfo.versionCode;
			return versionName;
		} catch (NameNotFoundException e) {
			return versionName;
		}
	}

	public static boolean ExistSDCard() {
		if (!Environment.getExternalStorageDirectory().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * -1:no network 0:wifi network 1:wap or other network 2:2G network 3:net
	 * network
	 * 
	 * @param context
	 * @return
	 */
	public static int getAPNType(Context context) {
		int netType = -1;
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		int subType = telephonyManager.getNetworkType();
		MyDebugUtils.d("telephonyType=" + subType);
		if (networkInfo == null || !networkInfo.isAvailable()) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			int NET_2G = 0;
			int NET_3G = 0;
			switch (subType) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				return NET_2G; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return NET_2G; // ~ 14-64 kbps
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return NET_2G; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				return NET_3G; // ~ 400-1000 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				return NET_3G; // ~ 600-1400 kbps
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return NET_2G; // ~ 100 kbps
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return NET_3G; // ~ 2-14 Mbps
			case TelephonyManager.NETWORK_TYPE_HSPA:
				return NET_3G; // ~ 700-1700 kbps
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				return NET_3G; // ~ 1-23 Mbps
			case TelephonyManager.NETWORK_TYPE_UMTS:
				return NET_3G; // ~ 400-7000 kbps
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				return NET_2G;
			default:
				return NET_2G;
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			int NET_WIFI = 0;
			netType = NET_WIFI;
		}
		return netType;
	}

	public static int getNetworkType(Context context) {
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		int type = 0;
		if (info != null) {

			MyDebugUtils.d("有网");
			if (info.getType() == ConnectivityManager.TYPE_MOBILE) {// 手机网络
				MyDebugUtils.d("TYPE_MOBILE");
				if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS
						|| info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {// 移动和联通的2G

					MyDebugUtils.d("移动和联通的2G");
					type = 2;
				}
				if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA) {// 电信的2G
					MyDebugUtils.d("电信的2G");
					type = 2;
				}
				if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_0) {// 电信的3G
					MyDebugUtils.d("电信的3G");
					type = 3;
				}
				if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_UMTS
						|| info.getSubtype() == TelephonyManager.NETWORK_TYPE_HSDPA) {// 联通的3G
					MyDebugUtils.d("联通的3G");
					type = 3;
				}
			} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {// wifi网络
				MyDebugUtils.d("TYPE_WIFI");
				type = 0;
			}
		} else {
			MyDebugUtils.d("没网");
			type = -1;
		}
		if (type >= 0) {// 有网>=0
			if (type == 0) {// wifi 0
				return type;
			} else if (type == 2) {// 2G网络2
				return type;
			} else if (type == 3) {// 3G网络3
				return type;
			}
		} else {// 没网-1
			return type;
		}
		return type;
	}

	/**
	 * MAC & open system time
	 * 
	 * @return
	 */
	public String[] getOtherInfo(Context context) {
		String[] other = { "null", "null" };
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo.getMacAddress() != null) {
			other[0] = wifiInfo.getMacAddress();
		} else {
			other[0] = "Fail";
		}
		// other[1] = getTimes(); ???
		return other;
	}

	public String getTimes(Context context) {
		long ut = SystemClock.elapsedRealtime() / 1000;
		if (ut == 0) {
			ut = 1;
		}
		int m = (int) ((ut / 60) % 60);
		int h = (int) ((ut / 3600));
		return h + " " + "info_times_hour" + m + " " + "info_times_minute";
	}

	/**
	 * 
	 * @param size
	 * @return
	 */
	public String formatSize(long size) {
		String suffix = null;
		float fSize = 0;

		if (size >= 1024) {
			suffix = "KB";
			fSize = size / 1024;
			if (fSize >= 1024) {
				suffix = "MB";
				fSize /= 1024;
			}
			if (fSize >= 1024) {
				suffix = "GB";
				fSize /= 1024;
			}
		} else {
			fSize = size;
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
		StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
		if (suffix != null)
			resultBuffer.append(suffix);
		return resultBuffer.toString();
	}

	/**
	 * 
	 * @return system version
	 */
	public static String getSystemVersion() {
		return Build.VERSION.INCREMENTAL.substring(1);
	}

	/**
	 * 
	 * @return system infos
	 */
	public String[] getVersion() {
		String[] version = { "null", "null", "null", "null" };
		String str1 = "/proc/version";
		String str2;
		String[] arrayOfString;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			version[0] = arrayOfString[2];// KernelVersion
			localBufferedReader.close();
		} catch (IOException e) {
		}
		version[1] = Build.VERSION.RELEASE;// firmware version
		version[2] = Build.MODEL;// model
		version[3] = Build.DISPLAY;// system version
		return version;
	}

	/**
	 * 
	 * @param context
	 * @return ram can use
	 */
	public static long getAvailMemory(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		return mi.availMem;
	}

	/**
	 * 
	 * @param context
	 * @return format to KB
	 */
	public static String getRomTotalSize(Context context) {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return Formatter.formatFileSize(context, blockSize * totalBlocks);
	}

	/**
	 * 
	 * @param context
	 * @return not format to KB
	 */
	public static long getRomBlockCount(Context context) {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long blockCount = stat.getBlockCount();
		return blockSize * blockCount;
	}

	public static String getRomAvailableSize(Context context) {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return Formatter.formatFileSize(context, blockSize * availableBlocks);
	}

	public static long getRomAvailableBlocks(Context context) {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return blockSize * availableBlocks;
	}

	public static long[] getRomMemroy() {
		long[] romInfo = new long[2];
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		// Total rom memory
		romInfo[0] = blockSize * stat.getBlockCount();
		// Available rom memory
		romInfo[1] = blockSize * stat.getAvailableBlocks();
		return romInfo;
	}

	public static String formatFileSize(Context context, long number) {
		return Formatter.formatFileSize(context, number);
	}

	public static String getSDTotalSize(Context context) {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return Formatter.formatFileSize(context, blockSize * totalBlocks);
	}

	public static long getSDBlockCount(Context context) {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long blockCount = stat.getBlockCount();
		return blockSize * blockCount;
	}

	public static String getSDAvailableSize(Context context) {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return Formatter.formatFileSize(context, blockSize * availableBlocks);
	}

	public static long getSDAvailableBlocks(Context context) {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return blockSize * availableBlocks;
	}

	public static long[] getSDCardMemory() {
		long[] sdCardInfo = new long[2];
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long bSize = sf.getBlockSize();
			long bCount = sf.getBlockCount();
			long availBlocks = sf.getAvailableBlocks();

			sdCardInfo[0] = bSize * bCount;// �ܴ�С
			sdCardInfo[1] = bSize * availBlocks;// ���ô�С
		}
		return sdCardInfo;
	}

	public static String[] getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" };
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return cpuInfo;
	}

	public static String getLocalIpAddress() {
		String addr = null;
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface network = en.nextElement();
				for (Enumeration<InetAddress> address = network
						.getInetAddresses(); address.hasMoreElements();) {
					InetAddress inetAddress = address.nextElement();
					if (inetAddress != null && !inetAddress.isLoopbackAddress()) {
						addr = inetAddress.getHostAddress();

						return addr == null ? "" : addr;
					}
				}
			}
		} catch (SocketException ex) {
			MyDebugUtils
					.d("getLocalIpAddress occur exception:" + ex.toString());
		}

		return addr;
	}

	public static void returnDesktop(Context context) { //
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static boolean checkTheNetworkConnection(Context context) { //
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static void getTotalMemory2() { // ͨ����
		Method readProclines = null;
		try {
			Class procClass;
			procClass = Class.forName("android.os.Process");
			Class parameterTypes[] = new Class[] { String.class,
					String[].class, long[].class };
			readProclines = procClass
					.getMethod("readProcLines", parameterTypes);
			Object arglist[] = new Object[3];
			final String[] mMemInfoFields = new String[] { "MemTotal:",
					"MemFree:", "Buffers:", "Cached:" };
			long[] mMemInfoSizes = new long[mMemInfoFields.length];
			mMemInfoSizes[0] = 30;
			mMemInfoSizes[1] = -30;
			arglist[0] = new String("/proc/meminfo");
			arglist[1] = mMemInfoFields;
			arglist[2] = mMemInfoSizes;
			if (readProclines != null) {
				readProclines.invoke(null, arglist);
				for (int i = 0; i < mMemInfoSizes.length; i++) {
					MyDebugUtils.d(mMemInfoFields[i] + " : " + mMemInfoSizes[i]
							/ 1024);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public static String getTotalMemory(Context context) {
		String str1 = "/proc/meminfo";
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");

			initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return Formatter.formatFileSize(context, initial_memory);
	}

	public static String getAvailMem(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		activityManager.getMemoryInfo(outInfo);
		long availMem = outInfo.availMem;
		boolean lowMemory = outInfo.lowMemory;
		long threshold = outInfo.threshold;
		return Formatter.formatFileSize(context, availMem);
	}

	public static boolean isGoogleMapAPI() {
		try {
			Class.forName("com.google.android.maps.MapActivity");
			return true;
		} catch (Exception e) { // google��ͼ������
			return false;
		}
	}

	public static Bitmap readPic(String path) {
		Bitmap bitmap = null;
		File file = new File(path);
		if (file.exists())
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		return bitmap;
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		// Log.i("AAAAA",":::"+ baos.toByteArray().length);
		return baos.toByteArray();
	}

	public static String getSDCardPath() {
		String SDCardPath;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			return SDCardPath = Environment.getExternalStorageDirectory()
					.toString();
		else
			return null;
	}

	public static String getSystemTime() {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return new SimpleDateFormat(pattern).format(new Date(System
				.currentTimeMillis()));
	}

	public static boolean checkSIMorWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
		boolean isWifiConnect = false;
		for (int i = 0; i < networkInfos.length; i++) {
			if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
				isWifiConnect = false;
			}
			if (networkInfos[i].getType() == connectivityManager.TYPE_WIFI) {
				isWifiConnect = true;
			}
		}
		return isWifiConnect;
	}

	/**
	 * http://www.android-doc.com/training/basics/network-ops/managing.html
	 * 
	 * @param context
	 */
	public static void checkaDevicesNetworkConnection(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = networkInfo.isConnected();
		MyDebugUtils.d("Wifi connected: " + isWifiConn);

	}

	public static void restartPackage(Context context, String packageName) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		activityManager.restartPackage(packageName);
	}

	public static void killBackgroundProcesses(Context context,
			String packageName) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		activityManager.killBackgroundProcesses(packageName);
	}

	public static void killProcess() {
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public static void exit(Context context, String packageName) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(startMain);
			System.exit(0);
		} else {// android2.1
			ActivityManager am = (ActivityManager) context
					.getSystemService(context.ACTIVITY_SERVICE);
			am.restartPackage(packageName);
		}
	}

	public static void finish(Activity activity) {
		activity.finish();
	}

	public static void toastShow(Context context, String content) {
		int duration = 0;
		Toast.makeText(context, content, duration).show();
	}

	/**
	 * get apps permissions
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 * @throws NameNotFoundException
	 */
	public static String[] getAppPermissions(Context context, String packageName)
			throws NameNotFoundException {
		return context.getPackageManager().getPackageInfo(packageName,
				PackageManager.GET_PERMISSIONS).requestedPermissions;
	}

	public static void goHome(Activity activity, Class<?> homeActivityClass) {
		activity.finish();
		Intent intent = new Intent(activity, homeActivityClass);
		intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
		activity.startActivity(intent);
	}

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
		} catch (NameNotFoundException e) {
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
			} catch (NameNotFoundException e) {
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
		} catch (NameNotFoundException e) {
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