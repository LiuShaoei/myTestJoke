package lzw.app.com.baselibrary.indicator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 底部跟踪的指示器
 * Created by LiuZhaowei on 2019/1/11 0011.
 */
public class IndicatorGroupView extends FrameLayout {
    //3.冬天的添加view - > 指示器的容器
    private LinearLayout mIndicatorGroup;
    private View mBottomTrackView;
    private int mItemWidth;//一个条目的宽度
    private int mIndicatorLeftMargin;
    private FrameLayout.LayoutParams mParams;//底部指示器的

    public IndicatorGroupView(Context context) {
        this(context, null);
    }

    public IndicatorGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorGroup = new LinearLayout(context);
        addView(mIndicatorGroup);


    }

    /**
     * 添加itemView
     *
     * @param itemView
     */
    public void addItemView(View itemView) {
        mIndicatorGroup.addView(itemView);
    }

    /**
     * 获取当前位置的item
     */
    public View getItemAt(int i) {
        return mIndicatorGroup.getChildAt(i);
    }

    /**
     * 添加底部跟踪指示器
     * 宽度和控件的宽度一样
     *
     * @param bottomTrackView
     */
    public void addBottomTrackView(View bottomTrackView, int itemWidth, int itemHeight) {
        if (bottomTrackView == null) {
            return;
        }
        this.mItemWidth = itemWidth;
        this.mBottomTrackView = bottomTrackView;
        //添加底部跟踪的View
        addView(mBottomTrackView);
        //要让他在底部, 宽度 ->一个条目的宽度
        //底部
        mParams = (LayoutParams) mBottomTrackView.getLayoutParams();
        mParams.gravity = Gravity.BOTTOM;
        mParams.width = mItemWidth;
        mParams.height = itemHeight;
    }

    /**
     * 添加底部指示器,宽度设置为当前文字最大宽度
     *
     * @param bottomTrackView
     * @param itemWidth
     * @param indicatorWidth
     * @param itemHeight
     */
    public void addBottomTrackView(View bottomTrackView, int itemWidth, int indicatorWidth, int itemHeight) {
        if (bottomTrackView == null) {
            return;
        }
        this.mItemWidth = itemWidth;
        this.mBottomTrackView = bottomTrackView;
        //添加底部跟踪的View
        addView(mBottomTrackView);
        //要让他在底部, 宽度 ->一个条目的宽度
        //底部
        mParams = (LayoutParams) mBottomTrackView.getLayoutParams();
        mParams.gravity = Gravity.BOTTOM;
        mParams.width = indicatorWidth;
        mParams.height = itemHeight;

        mIndicatorLeftMargin = (mItemWidth - indicatorWidth) / 2;
        mParams.leftMargin = mIndicatorLeftMargin;
    }

    /**
     * 滚动底部的指示器
     *
     * @param position
     * @param positionOffset
     */
    public void scrollBottomTrack(int position, float positionOffset) {
        int leftMargin = (int) ((position + positionOffset) * mItemWidth);
        mParams.leftMargin = leftMargin + mIndicatorLeftMargin;
        mBottomTrackView.setLayoutParams(mParams);
    }


    /**
     * 点击移动底部的指示器
     *
     * @param position
     */
    public void scrollBottomTrack(int position) {
        //要移动的最终位置
        int finalLeftMargin = position * mItemWidth + mIndicatorLeftMargin;
        //当前的位置
        int currentLeftMargin = mParams.leftMargin;
        //要移动的距离
        int distance = finalLeftMargin - currentLeftMargin;
        //设置动画
        ValueAnimator animator = ObjectAnimator.ofFloat(currentLeftMargin, finalLeftMargin)
                .setDuration((long) (Math.abs(distance) * 0.4f));
        animator.addUpdateListener((animation) -> {
            //会不断的回调这个方法,不断的设置leftMargin
            float leftMargin = (float) animation.getAnimatedValue();
            mParams.leftMargin = (int) leftMargin;
            mBottomTrackView.setLayoutParams(mParams);
        });
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();

    }
}
