package bj.vein.com.mylearning.myutils;

import android.app.Activity;
import android.content.Intent;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class MyActivityUtils {
	public static void goHome(Activity activity, Class<?> homeActivityClass) {
		activity.finish();
		Intent intent = new Intent(activity, homeActivityClass);
		intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
		activity.startActivity(intent);
	}
}
