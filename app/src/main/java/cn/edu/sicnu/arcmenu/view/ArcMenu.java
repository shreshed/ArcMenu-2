package cn.edu.sicnu.arcmenu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.sicnu.arcmenu.R;

/**
 * Created by HYM on 2018/5/15 0015.
 */

public class ArcMenu extends ViewGroup implements View.OnClickListener {

    private static final String TAG = "ArcMenu";

    private static final int POS_LEF_TOP = 0;
    private static final int POS_LEF_BOTTOM = 1;
    private static final int POS_RIGHT_TOP = 2;
    private static final int POS_RIGHT_BOTTOM = 3;
    private Position mPosition = Position.RIGHT_BOTTOM;
    private int mRadius;

    // 菜单的状态
    private Status mCurrentStatus = Status.CLOSE;

    // 菜单的主按钮
    private View mCButton;

    //
    private OnMenuItemClickListener mMenuItemClickListener;

    public enum Status {
        OPEN, CLOSE;
    }

    // 菜单的位置枚举类
    public enum Position {
        LEF_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM;
    }

    // 点击子菜单项的回调接口
    public interface OnMenuItemClickListener {
        void onClick(View view, int pos);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener mMenuItemClickListener) {
        this.mMenuItemClickListener = mMenuItemClickListener;
    }

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        // 获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);

        int pos = a.getInt(R.styleable.ArcMenu_position, 3);
        switch (pos) {
            case POS_LEF_TOP:
                mPosition = Position.LEF_TOP;
                break;

            case POS_LEF_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;

            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;

            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }
        mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
        Log.d(TAG, "ArcMenu: position=" + mPosition + ", radius=" + mRadius);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            // 测量child
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            layoutCButton();
        }
    }

    // 定位主菜单按钮
    private void layoutCButton() {
        mCButton = getChildAt(0);
        mCButton.setOnClickListener(this);
        int l = 0;
        int t = 0;
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();

        switch (mPosition) {
            case LEF_TOP:
                l = 0;
                t = 0;
                break;

            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;

            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;

            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        mCButton.layout(l, t, l + width, t + width);
    }

    @Override
    public void onClick(View view) {

    }
}
