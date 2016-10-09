//package android.vein.utils.libutils.view.pickerview.view;
//
//
//import com.mobcent.utils.DZResource;
//import com.mobcent.widget.pickerview.listener.OnDismissListener;
//import com.mobcent.widget.pickerview.utils.PickerViewAnimateUtil;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnTouchListener;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.FrameLayout;
//
///**
// * Created by Sai on 15/11/22.
// * 精仿iOSPickerViewController控件
// */
//public class BasePickerView {
//    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
//    );
//
//    private Context context;
//    protected FrameLayout contentContainer;
//    private ViewGroup decorView;//activity的根View
//    private FrameLayout rootView;//附加View 的 根View
//
//    private OnDismissListener onDismissListener;
//    private boolean isDismissing;
//
//    private Animation outAnim;
//    private Animation inAnim;
//    private int gravity = Gravity.BOTTOM;
//
//    public BasePickerView(Context context){
//        this.context = context;
//        resource = DZResource.getInstance(context);
//        initViews();
//        init();
//        initEvents();
//    }
//
//    protected void initViews(){
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        decorView = (ViewGroup) ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
//        rootView = (FrameLayout) layoutInflater.inflate(resource.getLayoutId("layout_basepickerview"), decorView, false);
//        rootView.setLayoutParams(new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
//        ));
//        contentContainer = (FrameLayout) rootView.findViewById(resource.getViewId("content_container"));
//        contentContainer.setLayoutParams(params);
//    }
//
//    protected void init() {
//        inAnim = getInAnimation();
//        outAnim = getOutAnimation();
//    }
//    protected void initEvents() {
//    }
//    /**
//     * show的时候调用
//     *
//     * @param view 这个View
//     */
//    private void onAttached(View view) {
//        decorView.addView(view);
//        contentContainer.startAnimation(inAnim);
//    }
//    /**
//     * 添加这个View到Activity的根视图
//     */
//    public void show() {
//        if (isShowing()) {
//            return;
//        }
//        onAttached(rootView);
//    }
//    /**
//     * 检测该View是不是已经添加到根视图
//     *
//     * @return 如果视图已经存在该View返回true
//     */
//    public boolean isShowing() {
//        View view = decorView.findViewById(resource.getViewId("outmost_container"));
//        return view != null;
//    }
//    public void dismiss() {
//        if (isDismissing) {
//            return;
//        }
//
//        //消失动画
//        outAnim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                decorView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //从activity根视图移除
//                        decorView.removeView(rootView);
//                        isDismissing = false;
//                        if (onDismissListener != null) {
//                            onDismissListener.onDismiss(BasePickerView.this);
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        contentContainer.startAnimation(outAnim);
//        isDismissing = true;
//    }
//    public Animation getInAnimation() {
//        int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, true);
//        return AnimationUtils.loadAnimation(context, res);
//    }
//
//    public Animation getOutAnimation() {
//        int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, false);
//        return AnimationUtils.loadAnimation(context, res);
//    }
//
//    public BasePickerView setOnDismissListener(OnDismissListener onDismissListener) {
//        this.onDismissListener = onDismissListener;
//        return this;
//    }
//
//    public BasePickerView setCancelable(boolean isCancelable) {
//        View view = rootView.findViewById(resource.getViewId("outmost_container"));
//
//        if (isCancelable) {
//            view.setOnTouchListener(onCancelableTouchListener);
//            contentContainer.setOnTouchListener(new OnTouchListener() {
//
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					return true;
//				}
//			});
//        }
//        else{
//            view.setOnTouchListener(null);
//        }
//        return this;
//    }
//    /**
//     * Called when the user touch on black overlay in order to dismiss the dialog
//     */
//    private final OnTouchListener onCancelableTouchListener = new OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                dismiss();
//            }
//            return false;
//        }
//    };
//
//	private DZResource resource;
//
//    public View findViewById(int id){
//        return contentContainer.findViewById(id);
//    }
//}
