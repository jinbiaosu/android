package android.vein.utils.libutils.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by vein on 16/8/25.
 */
public class JToastUtils {


    public static void toast(Context context, String message) {
        toast(context, message, Toast.LENGTH_LONG);
    }

    public static void toast(Context context, String message, int time) {
        Toast.makeText(context, message, time).show();
    }

    public static void toastByResName(Context context, String resName) {
        toastByResName(context, resName, Toast.LENGTH_LONG);
    }

    public static void toastByResName(Context context, String resName, int time) {
        Toast.makeText(context, JResourceUtils.getInstance(context.getApplicationContext()).getString(resName), time).show();
    }

}
