package lzw.app.com.baselibrary.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lzw.app.com.baselibrary.R;

/**
 * Created by LiuZhaowei on 2018/12/14 0014.
 */
public class BannerView extends RelativeLayout {
    //轮播的viewPager
    private BannerViewPager mBannerVp;
    //轮播的描述
    private TextView mBannerDescTv;
    //点的容器
    private LinearLayout mBannerDotView;

    //初始化点的指示器,点选中的drawable
    private Drawable mIndicatorFocusDrawable;
    private Drawable mIndicatorDefaultDrawable;
    //自定义的bannerAdapter;
    private BannerAdapter mAdapter;
    private Context mContext;
    private int mDotSize = 8;//点的默认大小
    private int mDotDistance = 10;//点的之间距离
    private int mBottomHeight = 35;//底部透明部分的高度
    private int mDotGravity = -1;//自定义属性,点的显示位置,默认是左边
    private RelativeLayout mRelativeLayout;//底部颜色
    private int mBottomColor = Color.TRANSPARENT;//默认透明

    //当前点点亮的位置
    private int mCurrentPosition = 0;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //初始化自定义属性
        initAttribute(attrs);
        //把布局加载到这个view中
        inflate(context, R.layout.ui_banner_layout, this);
        initView();


    }

    /**
     * 初始化自定义属性
     *
     * @param attrs
     */
    private void initAttribute(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        mDotGravity = array.getInt(R.styleable.BannerView_dotGravity, mDotGravity);
        mIndicatorFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if (mIndicatorFocusDrawable == null) {
            //如果在点的布局文件中没有配置点的颜色,我们就给他一个默认值
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        }
        mIndicatorDefaultDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if (mIndicatorDefaultDrawable == null) {
            mIndicatorDefaultDrawable = new ColorDrawable(Color.GREEN);
        }
        //获取点的大小和距离,有
        mDotSize = (int) array.getDimension(R.styleable.BannerView_dotSize, dip2px(mDotSize));
        mDotDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance, dip2px(mDotDistance));
        mBottomColor = array.getColor(R.styleable.BannerView_bottomColor, mBottomColor);
        mBottomHeight = (int) array.getDimension(R.styleable.BannerView_bottomHeight, dip2px(mBottomHeight));
        array.recycle();

    }

    /**
     * 初始化view
     */
    private void initView() {
        mBannerVp = findViewById(R.id.banner_view);
        mBannerDescTv = findViewById(R.id.banner_desc_tv);
        mBannerDotView = findViewById(R.id.banner_dot_container);
        mRelativeLayout = findViewById(R.id.banner_bottom_view);
        mRelativeLayout.setBackgroundColor(mBottomColor);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRelativeLayout.getLayoutParams();
        params.height = mBottomHeight;
        mRelativeLayout.setLayoutParams(params);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        mBannerVp.setAdapter(adapter);
        //初始化点
        initDotIndicator();
        //初始化广告的描述

        //bug修复
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //监听当前选定的位置
                pageSelect(position);
            }
        });
        //初始化的时候获取第一条的描述
        String firstDesc = mAdapter.getBannerDesc(0);
        mBannerDescTv.setText(firstDesc);
    }

    /**
     * ]
     * 页面切换的回调
     *
     * @param position
     */
    private void pageSelect(int position) {
        //把当前位置的点点亮 广告位相等
        DotIndicatorView oldIndicatorView =
                (DotIndicatorView) mBannerDotView.getChildAt(mCurrentPosition);

        oldIndicatorView.setDrawable(mIndicatorDefaultDrawable);
        //把之前亮着的点设置位默认
        mCurrentPosition = position % mAdapter.getCount();
        DotIndicatorView newIndicatorView = (DotIndicatorView) mBannerDotView.getChildAt(mCurrentPosition);
        ;
        newIndicatorView.setDrawable(mIndicatorFocusDrawable);

        //设置广告描述
        String bannerDesc = mAdapter.getBannerDesc(mCurrentPosition);
        mBannerDescTv.setText(bannerDesc);
    }

    /**
     * 初始化点的指示器
     */
    private void initDotIndicator() {
        //获取广告位的数量
        int count = mAdapter.getCount();
        mBannerDotView.setGravity(getDotGravity());
        for (int i = 0; i < count; i++) {
            //不断的往点的指示器添加内容
            DotIndicatorView indicatorView = new DotIndicatorView(mContext);
            //设置点的大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotSize, mDotSize);
            params.leftMargin = params.rightMargin = mDotDistance;//设置左右间距
            indicatorView.setLayoutParams(params);
            if (i == 0) {
                indicatorView.setDrawable(mIndicatorFocusDrawable);
            } else {
                indicatorView.setDrawable(mIndicatorDefaultDrawable);
            }

            mBannerDotView.addView(indicatorView);
        }
    }

    /**
     * 获取点的位置
     *
     * @return
     */
    private int getDotGravity() {
        switch (mDotGravity) {
            case 0:
                return Gravity.CENTER;
            case -1:
                return Gravity.LEFT|Gravity.CENTER_VERTICAL;
            case 1:
                return Gravity.RIGHT|Gravity.CENTER_VERTICAL;
            default:
                return Gravity.CENTER;
        }
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
    }

    /**
     * 开始滚动
     */
    public void startRoll() {
        mBannerVp.startRoll();
    }
}
