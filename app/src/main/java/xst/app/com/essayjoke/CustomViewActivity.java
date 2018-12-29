package xst.app.com.essayjoke;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import xst.app.com.baselibrary.ioc.OnClick;
import xst.app.com.baselibrary.ioc.ViewById;
import xst.app.com.essayjoke.custom.ColorChangeTextView;
import xst.app.com.essayjoke.custom.QQStepView;
import xst.app.com.essayjoke.fragment.ItemFragment;
import xst.app.com.framelibrary.BaseSkinActivity;
import xst.app.com.framelibrary.DefaultNavigationBar;

/**
 * Created by LiuZhaowei on 2018/12/12 0012.
 */
public class CustomViewActivity extends BaseSkinActivity {
    @ViewById(R.id.step_view)
    private QQStepView mQQStepView;
    @ViewById(R.id.color_change_text)
    private ColorChangeTextView mColorChangeText;
    @ViewById(R.id.view_pager)
    private ViewPager mViewPager;
    @ViewById(R.id.linear_layout)
    private LinearLayout mLinearLayout;


    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private List<ColorChangeTextView> mIndicators;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom;
    }

    @Override
    protected void initView() {
        mIndicators = new ArrayList<>();
        initIndicator();
        initViewPager();
    }

    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            //动态添加颜色跟踪的TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorChangeTextView tv = new ColorChangeTextView(this);
            //设置颜色
            tv.setTextSize(30);
            tv.setChangeColor(Color.RED);
            tv.setText(items[i]);
            tv.setLayoutParams(params);
            //把新的加入到LinearLayout容器
            mLinearLayout.addView(tv);
            //加入集合
            mIndicators.add(tv);
        }
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
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ColorChangeTextView left = mIndicators.get(position);
                left.setDirection(ColorChangeTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1 - positionOffset);
                try {
                    ColorChangeTextView right = mIndicators.get(position + 1);
                    right.setDirection(ColorChangeTextView.Direction.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void initData() {
        mQQStepView.setStepMax(4000,3000);
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
        if (viewId == R.id.left_to_right) {
            mColorChangeText.setDirection(ColorChangeTextView.Direction.LEFT_TO_RIGHT);
            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(2000);
            valueAnimator.addUpdateListener((animation -> {
                float currentProgress = (float) animation.getAnimatedValue();
                mColorChangeText.setCurrentProgress(currentProgress);

            }));
            valueAnimator.start();
        } else {
            mColorChangeText.setDirection(ColorChangeTextView.Direction.RIGHT_TO_LEFT);
            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(2000);
            valueAnimator.addUpdateListener((animation -> {
                float currentProgress = (float) animation.getAnimatedValue();
                mColorChangeText.setCurrentProgress(currentProgress);

            }));
            valueAnimator.start();
        }
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("自定义View").builder();
    }
}
