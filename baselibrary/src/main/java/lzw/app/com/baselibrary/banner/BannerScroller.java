package lzw.app.com.baselibrary.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by LiuZhaowei on 2018/12/14 0014.
 */
public class BannerScroller extends Scroller {

    private int mScrollerDuration = 950;//动画持续的时间

    /**
     * 设置翻页的时间
     *
     * @param scrollerDuration
     */
    public void setScrollerDuration(int scrollerDuration) {
        this.mScrollerDuration = scrollerDuration;
    }

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    /**
     * 复写这个控制方法,控制翻页持续的时间
     *
     * @param startX
     * @param startY
     * @param dx
     * @param dy
     * @param duration
     */

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollerDuration);
    }
}
