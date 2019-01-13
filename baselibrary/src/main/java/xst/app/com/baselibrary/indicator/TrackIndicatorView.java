package xst.app.com.baselibrary.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import xst.app.com.baselibrary.R;

/**
 * viewPager指示器
 * Created by LiuZhaowei on 2019/1/11 0011.
 */
public class TrackIndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    //adapter的设计模式
    private IndicatorAdapter mAdapter;
    //一屏幕可见多少个
    private int mTabVisibleNums;
    //默认的下标指示器的高度
    private int mIndicatorHeight = 2;
    //下标指示器的宽度
    private int mItemWidth;
    //下标指示器的颜色
    private int mIndicatorColor;
    //0代表和文字一样的宽度,这里是按照最大的文字宽度计算,1代表和控件一样的宽度.
    private int mIndicatorWidth = 1;
    //是否显示下标指示器
    private boolean mIndicatorBoolean;
    //需要联动的viewPager
    private ViewPager mViewPager;
    private Context mContext;
    //判断是滑动还是点击,执行的方法
    private boolean mIsExecuteScroll = false;
    //指示器条目的容器,因为HorizontalScrollView 只能添加一个view ,
    // 所以把所有条目放到LinearLayout指示器中,
    // 然后把LinearLayout指示器放到HorizontalScrollView中
    private IndicatorGroupView mIndicatorGroup;
    //当前的位置
    private int mCurrentPosition = 0;

    public TrackIndicatorView(Context context) {
        this(context, null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //3.初始化指示器的容器
        mIndicatorGroup = new IndicatorGroupView(context);
        addView(mIndicatorGroup);
        //4.指定item宽度,实现可以定显示的条数,多余滑动
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);
        mTabVisibleNums = typedArray.getInt(R.styleable.TrackIndicatorView_tabVisibleNum, mTabVisibleNums);
        mIndicatorHeight = (int) typedArray.getDimension(R.styleable.TrackIndicatorView_indicatorHeight, mIndicatorHeight);
        mIndicatorBoolean = typedArray.getBoolean(R.styleable.TrackIndicatorView_indicatorBoolean, false);
        mIndicatorColor = typedArray.getColor(R.styleable.TrackIndicatorView_indicatorColor, Color.RED);
        mIndicatorWidth = typedArray.getInt(R.styleable.TrackIndicatorView_indicatorWidth, mIndicatorWidth);
        typedArray.recycle();
    }

    /**
     * 1.设置一个适配器
     *
     * @param adapter
     */
    public void setAdapter(IndicatorAdapter adapter, boolean isClick) {
        if (adapter == null) {
            throw new NullPointerException("lzw adapter is null");
        }
        this.mAdapter = adapter;
        //2.动态添加view
        int itemCount = mAdapter.getCount();//得到条目数
        //循环添加view
        for (int i = 0; i < itemCount; i++) {
            View itemView = mAdapter.getView(i, this);
            mIndicatorGroup.addItemView(itemView);
            if (i == 0) {
                mAdapter.setClickChangeColor(itemView, 1);
            }
            //6.设置点击事件
            if (mViewPager != null && isClick) {
                switchItemClick(itemView, i);
            }
        }
        //默认点亮第一个位置

    }


    /**
     * indicator和viewpager一起联动,设置点击事件
     *
     * @param view
     * @param position
     */
    private void switchItemClick(View view, int position) {
        view.setOnClickListener((v) -> {
            mViewPager.setCurrentItem(position, false);
            // 移动indicatorView
            smoothScrollIndicator(position);
        });
    }

    /**
     * 点击移动的方法,带动画
     *
     * @param position
     */
    private void smoothScrollIndicator(int position) {
        float totalScroll = (position) * mItemWidth;
        //左边的便宜
        int offsetScroll = (getWidth() - mItemWidth) / 2;
        //最终的一个偏移量
        final int finalScroll = (int) (totalScroll - offsetScroll);
        //电泳scrollview自带的方法,而且带动画
        smoothScrollTo(finalScroll, 0);
        //移动下标指示器
        mIndicatorGroup.scrollBottomTrack(position);
    }

    public void setAdapter(IndicatorAdapter adapter, ViewPager viewPager, boolean isClick) {
        if (viewPager == null) {
            throw new NullPointerException("viewPager is null!");
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        setAdapter(adapter, isClick);
    }

    public void setAdapter(IndicatorAdapter adapter, ViewPager viewPager) {
        if (viewPager == null) {
            throw new NullPointerException("viewPager is null!");
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        setAdapter(adapter, true);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //这里是测量完毕,可以拿到控件的宽高
        if (changed && mItemWidth == 0) {
            //指定item的宽度,这里是测量完毕
            mItemWidth = getItemWidth();
            //循环指定item的宽度
            for (int i = 0; i < mAdapter.getCount(); i++) {
                mIndicatorGroup.getItemAt(i).getLayoutParams().width = mItemWidth;
                //6.设置点击事件
            }
            //需要添加底部跟踪的指示器
            if (mIndicatorBoolean) {
                View view = new View(mContext);
                view.setBackgroundColor(mIndicatorColor);
                if (mIndicatorWidth == 0) {//如果为0,设置指示器的宽度为文字的宽度,这里的以最大的文字宽度为准
                    //获取指示器的宽度
                    int mIndicatorWidth = 0;
                    for (int i = 0; i < mAdapter.getCount(); i++) {
                        int currentItemWidth = mIndicatorGroup.getItemAt(i).getWidth();
                        mIndicatorWidth = Math.max(currentItemWidth, mIndicatorWidth);
                    }
                    mIndicatorGroup.addBottomTrackView(view, mItemWidth, mIndicatorWidth, mIndicatorHeight);
                    return;
                }
                mIndicatorGroup.addBottomTrackView(view, mItemWidth, mIndicatorHeight);
            }
        }
    }

    /**
     * 获取item的配置
     * 如果没有配置tabvisiblenum ,
     * 获取就是0 有时候不希望, 这里写一个算法计算
     * 取item中最宽的一个为高度,如果以此为高度所有的item的宽度加起来不足一屏幕,则设置位一屏幕为标准
     *
     * @return
     */
    private int getItemWidth() {
        //1.判断有没有指定
        int parentWidth = getWidth();

        //获取最宽的
        int maxItemWidth = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            int currentItemWidth = mIndicatorGroup.getItemAt(i).getWidth();
            maxItemWidth = Math.max(currentItemWidth, maxItemWidth);
        }
        if (mTabVisibleNums != 0) {
            //当设置的5个数加起来的宽度超过一整屏幕,则设置不生效
            if (maxItemWidth * mTabVisibleNums <= parentWidth) {
                return parentWidth / mTabVisibleNums;
            }
        }
        //宽度就是获取最宽的一个
        if (maxItemWidth * mAdapter.getCount() < parentWidth) {
            //代表所有的item加起来不足一屏幕
            maxItemWidth = parentWidth / mAdapter.getCount();
        }
        return maxItemWidth;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int i1) {
        if (mIsExecuteScroll) {
            //滚动的时候会不断的调用
            scrollCurrentIndicator(position, positionOffset);
            mAdapter.setOnPageScroll(mIndicatorGroup.getItemAt(position), mIndicatorGroup.getItemAt(position + 1), positionOffset);
            if (mIndicatorBoolean) {
                mIndicatorGroup.scrollBottomTrack(position, positionOffset);
            }
        }
    }

    /**
     * 不多滚动当前指示器
     *
     * @param position
     * @param positionOffset
     */
    private void scrollCurrentIndicator(int position, float positionOffset) {
        float totalScroll = (position + positionOffset) * mItemWidth;
        //左边的便宜
        int offsetScroll = (getWidth() - mItemWidth) / 2;
        //最终的一个偏移量
        final int finalScroll = (int) (totalScroll - offsetScroll);
        scrollTo(finalScroll, 0);
    }

    @Override
    public void onPageSelected(int position) {
        //滚动完成后调用,在这里重新设置颜色
        //上一个位置重置,将当前位置点亮
        mAdapter.setClickChangeColor(mIndicatorGroup.getItemAt(mCurrentPosition), 0);
        mCurrentPosition = position;
        //将当前位置点亮
        mAdapter.setClickChangeColor(mIndicatorGroup.getItemAt(mCurrentPosition), 1);
    }

    @Override
    public void onPageScrollStateChanged(int position) {
        //1.是滑动
        if (position == 1) {
            mIsExecuteScroll = true;
        }
        //2.是点击切换
        if (position == 0) {
            //点击切换,把当前位置的效果去掉,设置新的效果
            mIsExecuteScroll = false;
        }
    }
}
