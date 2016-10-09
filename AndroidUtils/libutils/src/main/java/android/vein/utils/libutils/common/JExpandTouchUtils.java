package android.vein.utils.libutils.common;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;

/**
 * Created by vein on 16/8/25.
 */
public class JExpandTouchUtils {
    public static void createTouchDelegate(final View view, final int expandTop, final int expandBottom, final int expandLeft, final int expandRight) {
        final View parent = (View) view.getParent();
        parent.post(new Runnable() {
            public void run() {
                final Rect r = new Rect();
                view.getHitRect(r);
                r.top -= expandTop;
                r.bottom += expandBottom;
                r.left -= expandLeft;
                r.right += expandRight;
                parent.setTouchDelegate(new TouchDelegate(r, view));
            }
        });
    }

    /**
     * 扩大点击区域
     * @param view
     * @param expandTouchWidth
     */
    public static void createTouchDelegate(final View view, final int expandTouchWidth) {
        final View parent = (View) view.getParent();
        parent.post(new Runnable() {
            public void run() {
                final Rect r = new Rect();
                view.getHitRect(r);
                r.top -= expandTouchWidth;
                r.bottom += expandTouchWidth;
                r.left -= expandTouchWidth;
                r.right += expandTouchWidth;
                parent.setTouchDelegate(new TouchDelegate(r, view));
            }
        });
    }
}

