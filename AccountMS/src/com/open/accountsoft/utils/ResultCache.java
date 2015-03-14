
package com.open.accountsoft.utils;

import android.content.Context;
import android.content.SharedPreferences;

// 保存结果以备 UT 使用。
public class ResultCache {
    public static String getMacKey(Context context, int scope) {
        return getSP(context, scope).getString("mackey", null);
    }

    public static void setMacKey(Context context, int scope, String macKey) {
        getSP(context, scope).edit().putString("mackey", macKey).commit();
    }

    public static String getAccessToken(Context context, int scope) {
        return getSP(context, scope).getString("accessToken", null);
    }

    public static void setAccessToken(Context context, int scope, String accessToken) {
        getSP(context, scope).edit().putString("accessToken", accessToken).commit();
    }

    private static SharedPreferences getSP(Context context, int scope) {
        return context.getApplicationContext().getSharedPreferences("scope" + scope,
                Context.MODE_PRIVATE);
    }
}
