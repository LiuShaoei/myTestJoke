package xst.app.com.baselibrary.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by LiuZhaowei on 2018/12/14 0014.
 */
public class BannerViewPager extends ViewPager {
    //自定义的adapter;
    private BannerAdapter mAdapter;
    //实现自动轮播 发送消息的msgWhat
    private final int SCROLL_MSG = 0X0011;
    //页面切换的间隔时间
    private int mCutDownTime = 3500;
    //自定义的页面切换的scroller
    private BannerScroller mBannerScroller;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //每隔多少秒后切换到下一页
            setCurrentItem(getCurrentItem() + 1);
            //不断的循环轮行
            startRoll();
        }
    };

    public BannerViewPager(@NonNull Context context) {
        this(context, null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //改变viewPager切换的速率
        //duration ->改变mScroller ,发现源码是private,只能通过反射拿到类去设置

        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            //要去设置参数
            field.setAccessible(true);
            mBannerScroller = new BannerScroller(context);
            //第一个参数代表当前属性在哪个类,第二个参数代表要设置的值
            field.set(this, mBannerScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置页面翻页的时间
     *
     * @param scrollerDuration
     */
    public void setScrollerDuration(int scrollerDuration) {
        mBannerScroller.setScrollerDuration(scrollerDuration);
    }

    /**
     * 设置自定义的BannerAdapter
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        //设置父类viewPager的adapter
        mAdapter = adapter;
        setAdapter(new BannerPagerAdapter());

    }

    /**
     * 实现自动轮播
     */
    public void startRoll() {
        //清除消息,防止很多地方调用发生错乱
        mHandler.removeMessages(SCROLL_MSG);
        //第一个参数是消息内容,第二个是延迟时间
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
    }

    /**
     * 给viewPager设置适配器
     */
    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;//为了实现无限循环
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;//官方推荐写法
        }

        /**
         * 创建viewPager条目回调的方法
         *
         * @param container
         * @param position
         * @return
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            //采用adapter的设计模式,是为了完全让用户自定义.
            View bannerItemView = mAdapter.getView(position % mAdapter.getCount());
            //添加到viewPager
            container.addView(bannerItemView);
            return bannerItemView;
        }

        /**
         * 销毁条目回调的方法
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            object = null;//释放内存
        }
    }

    /**
     * 销毁停止handler,防止内存泄露.生命周期不一样.更长
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
    }
}
