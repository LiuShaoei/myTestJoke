package lzw.app.com.essayjoke;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lzw.app.com.baselibrary.indicator.ColorChangeTextView;
import lzw.app.com.baselibrary.indicator.IndicatorAdapter;
import lzw.app.com.baselibrary.indicator.TrackIndicatorView;
import lzw.app.com.baselibrary.ioc.OnClick;
import lzw.app.com.baselibrary.ioc.ViewById;
import lzw.app.com.essayjoke.custom.QQStepView;
import lzw.app.com.essayjoke.fragment.ItemFragment;
import lzw.app.com.framelibrary.BaseSkinActivity;
import lzw.app.com.framelibrary.DefaultNavigationBar;

/**
 * Created by LiuZhaowei on 2018/12/12 0012.
 */
public class CustomViewActivity extends BaseSkinActivity {
    @ViewById(R.id.step_view)
    private QQStepView mQQStepView;
    // @ViewById(R.id.color_change_text)
    // private ColorChangeTextView mColorChangeText;
    @ViewById(R.id.view_pager)
    private ViewPager mViewPager;
    @ViewById(R.id.track_indicator_view)
    private TrackIndicatorView mTrackIndicatorView;
    private String[] items = {"直播", "推荐", "视频", "图小图图", "段子", "图精华", "直播", "推荐", "视频", "图片", "段子", "精华"};
    private int textSize = 20;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom;
    }

    @Override
    protected void initView() {
        initViewPager();
        initIndicator();

    }

    private void initIndicator() {

        mTrackIndicatorView.setAdapter(new IndicatorAdapter() {
            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                ColorChangeTextView tv = new ColorChangeTextView(CustomViewActivity.this);
                //TextView tv = new TextView(CustomViewActivity.this);
                //设置颜色
                tv.setTextSize(textSize);
                tv.setTextColor(Color.GRAY);
                tv.setGravity(Gravity.CENTER);
                tv.setText(items[position]);
                tv.setLayoutParams(params);
                return tv;
            }

            //            @Override
//            public void setClickChangeColor(View view, int state) {
//                TextView tv = (TextView) view;
//                if(state == 0){
//                    tv.setTextColor(Color.GRAY);
//                }else{
//                    tv.setTextColor(Color.RED);
//                }
//            }
            @Override
            public void setClickChangeColor(View view, int state) {
                ColorChangeTextView tv = (ColorChangeTextView) view;
                if (state == 0) {
                    tv.setResetTextColor(Color.GRAY);
                    tv.setTextSize(textSize);
                } else {
                    tv.setClickTextColor(Color.RED);
                    tv.setTextSize(textSize);
                }
            }

            @Override
            public void setOnPageScroll(View view1, View view2, float positionOffset) {
                ColorChangeTextView left = (ColorChangeTextView) view1;
                left.setDirection(ColorChangeTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1 - positionOffset);
                try {
                    ColorChangeTextView right = (ColorChangeTextView) view2;
                    right.setDirection(ColorChangeTextView.Direction.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, mViewPager);

    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i("lzw","onPageScrolled");
//                ColorChangeTextView left = mIndicators.get(position);
//                left.setDirection(ColorChangeTextView.Direction.RIGHT_TO_LEFT);
//                left.setCurrentProgress(1 - positionOffset);
//                try {
//                    ColorChangeTextView right = mIndicators.get(position + 1);
//                    right.setDirection(ColorChangeTextView.Direction.LEFT_TO_RIGHT);
//                    right.setCurrentProgress(positionOffset);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
    }

    @Override
    protected void initData() {
        mQQStepView.setStepMax(4000, 3000);
//        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 3000);
//        valueAnimator.setDuration(3000);
//        valueAnimator.setInterpolator(new DecelerateInterpolator());//这里是插值器,实现先满后快
//        valueAnimator.addUpdateListener((animation -> {
//            float currentStep = (float) animation.getAnimatedValue();
//            mQQStepView.setCurrentStep((int) currentStep);
//        }));
        //valueAnimator.start();
    }

    @OnClick({R.id.right_to_left, R.id.left_to_right})
    private void onClick(View view) {
        int viewId = view.getId();
//        if (viewId == R.id.left_to_right) {
//            mColorChangeText.setDirection(ColorChangeTextView.Direction.LEFT_TO_RIGHT);
//            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
//            valueAnimator.setDuration(2000);
//            valueAnimator.addUpdateListener((animation -> {
//                float currentProgress = (float) animation.getAnimatedValue();
//                mColorChangeText.setCurrentProgress(currentProgress);
//
//            }));
//            valueAnimator.start();
//        } else {
//            mColorChangeText.setDirection(ColorChangeTextView.Direction.RIGHT_TO_LEFT);
//            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
//            valueAnimator.setDuration(2000);
//            valueAnimator.addUpdateListener((animation -> {
//                float currentProgress = (float) animation.getAnimatedValue();
//                mColorChangeText.setCurrentProgress(currentProgress);
//
//            }));
//            valueAnimator.start();
//        }
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("自定义View").builder();
    }
}
