package android.vein.utils.androidutils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by vein on 16/9/14.
 */
public class NoDataView extends RelativeLayout{
    private ImageView mImageView;
    private TextView mTextView1;
    private TextView mTextView2;
    public NoDataView(Context context) {
        super(context);
    }

    public NoDataView(Context context, AttributeSet attrs) {
        this(context,null,0);
    }

    public NoDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(Context context){
        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)getLayoutParams();
        mImageView=new ImageView(context);
        mTextView1=new TextView(context);
        mTextView2=new TextView(context);
    }
}
