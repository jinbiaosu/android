package android.vein.utils.androidutils;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.vein.utils.libutils.common.JListUtils;
import android.vein.utils.libutils.common.JPhoneUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by vein on 16/8/26.
 */
public class VerticalTabWidget extends LinearLayout{
    private LinearLayout mRootLayout;
    private Context mContext;
    private int mRootHeigth=100;

    public VerticalTabWidget(Context context) {
        this(context,null);
    }

    public VerticalTabWidget(Context context, AttributeSet attrs) {
       this(context,attrs,0);
    }

    public VerticalTabWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        this.mContext=context;
        this.setOrientation(HORIZONTAL);
    }


    public  void initViews(List<TabViewModel> tabs){
        if (JListUtils.isEmpty(tabs)){
            Logger.e("No tab data , need constructor data");
            return;
        }
        mRootLayout=new LinearLayout(mContext);
        mRootLayout.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams rootParams=new LinearLayout.LayoutParams(JPhoneUtils.getDisplayWidth(mContext), mRootHeigth);
        mRootLayout.setLayoutParams(rootParams);
        this.addView(mRootLayout);

        RelativeLayout childParent;
        for (int i=0;i<tabs.size();i++){
            //每个子的最外层布局
            childParent=new RelativeLayout(mContext);
            RelativeLayout.LayoutParams childParentLp=new RelativeLayout.LayoutParams(JPhoneUtils.getDisplayWidth(mContext)/tabs.size(), mRootHeigth);
            childParent.setLayoutParams(childParentLp);
//
            ImageView childImageView=new ImageView(mContext);
            childImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            childImageView.setBackground(JResourceUtils.getInstance(mContext).getDrawable(tabs.get(i).getTabIcon()));
            childImageView.setBackgroundResource(R.drawable.mc_forum_main_bar_button1_h);

            RelativeLayout.LayoutParams childIconLayout=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, mRootHeigth-JPhoneUtils.dip2px(2));
            childIconLayout.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
            childParent.addView(childImageView,childIconLayout);

            TextView childTextView=new TextView(mContext);
            childTextView.setTextColor(Color.RED);
            childTextView.setTextSize(13);
            childTextView.setIncludeFontPadding(false);
            childTextView.setText(tabs.get(i).getTabText());

            RelativeLayout.LayoutParams childTextLayout=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,mRootHeigth);
            childTextLayout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
            childTextLayout.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
            childParent.addView(childTextView);

            mRootLayout.addView(childParent);
        }
    }
}
