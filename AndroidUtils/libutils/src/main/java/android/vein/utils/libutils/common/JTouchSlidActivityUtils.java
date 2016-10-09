package android.vein.utils.libutils.common;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by vein on 16/8/25.
 * Activity侧滑退出帮助类，用法：首先需要初始化对象setDelegate(),然后重写activity如下方法dispatchTouchEvent¬
 */
public class JTouchSlidActivityUtils {
    //    private void useDemo(){
//        JTouchSlidActivityUtils  slideHelper = new JTouchSlidActivityUtils(this);
//        slideHelper.setDelegate(this);
//        @Override
//        public boolean dispatchTouchEvent(MotionEvent ev) {
//            try {
//                if (slideHelper.dispatchTouchEvent(ev)) {
//                    return true;
//                }
//                return super.dispatchTouchEvent(ev);
//            } catch (Exception e) {
//                return true;
//            }
//        }
//    }

    private float startX, startY, touchSlop;
    private int slideMax;
    private boolean isSliding = false;
    private float disX = 0, disY = 0;

    private TouchSlideDelegate delegate;

    public interface TouchSlideDelegate {

        boolean isSlideAble();

        boolean isSlideFullScreen();

        void slideExit();

    }

    public TouchSlideDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(TouchSlideDelegate delegate) {
        this.delegate = delegate;
    }

    public JTouchSlidActivityUtils(Context context) {
        this.slideMax = JPhoneUtils.getDisplayWidth(context.getApplicationContext()) / 3;
        this.touchSlop = ViewConfiguration.get(context.getApplicationContext()).getScaledTouchSlop();
    }

    /**
     * @param ev
     * @return true 代表拦截 false不拦截需要掉activity的super.dispatchTouchEvent()
     */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getDelegate() == null || !getDelegate().isSlideAble()) {
            return false;
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                resetSlidParams();
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                disX = ev.getX() - startX;
                disY = ev.getY() - startY;
                if (Math.abs(disX) > Math.abs(disY) && disX > touchSlop) {
                    if (getDelegate().isSlideFullScreen()) {
                        isSliding = true;
                        return true;
                    } else {
                        if (startX <= touchSlop) {
                            isSliding = true;
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isSliding) {
                    if (Math.abs(disX) > Math.abs(disY) && disX > slideMax) {
                        getDelegate().slideExit();
                        resetSlidParams();
                        return true;
                    }
                    return false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                resetSlidParams();
                break;

            default:
                break;
        }
        return false;
    }

    private void resetSlidParams() {
        disX = 0;
        disY = 0;
        startX = 0;
        startY = 0;
        isSliding = false;
    }


}
