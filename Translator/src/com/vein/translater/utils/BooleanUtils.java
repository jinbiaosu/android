package com.vein.translater.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class BooleanUtils {
	public static boolean isNull(String value) {
		value = value.trim();
		if (value == null || "".equals(value)) {
			return true;
		}

		return false;
	}
	public static boolean isnetWorkAvilable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null) {
            Log.e("FlyleafActivity", "couldn't get connectivity manager");
        } else {
            NetworkInfo [] networkInfos = connectivityManager.getAllNetworkInfo();
            if(networkInfos != null){
                for (int i = 0, count = networkInfos.length; i < count; i++) {
                    if(networkInfos[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
