package xst.app.com.baselibrary.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import xst.app.com.baselibrary.R;


/**
 * h5加载页面动画
 */
public class LoadingBounceView extends LinearLayout {

    private ShapeBounceView mShapeView;
    private View mView;
    private int mTranslationDistance = 0;
    private final long ANIMATOR_DURATION = 400;

    public LoadingBounceView(Context context) {
        this(context, null);
    }

    public LoadingBounceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingBounceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTranslationDistance = dip2px(80);
        initLayout();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        //1.加载写好的布局
        //1.1实例化view添加到该view .后面的this就是代表把这个布局,加载到LoadingView中.
        inflate(getContext(), R.layout.ui_loading_view, this);
        mShapeView = findViewById(R.id.shape_view);//上面的形状
        mView = findViewById(R.id.view);//中间的阴影
        startFallAnimator();


    }

    /**
     * 开始下落位移动画
     */
    private void startFallAnimator() {
        //动画作用在谁的身上
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat
                (mShapeView, "translationY", 0, mTranslationDistance);
        translationAnimator.setDuration(ANIMATOR_DURATION);
        // translationAnimator.start();
        //配合中间阴影缩小
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mView, "scaleX", 1f, 0.3f);
        scaleAnimator.setDuration(ANIMATOR_DURATION);
        // scaleAnimator.start();

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateInterpolator());
        //插入一个插值器,实现先快后慢,先慢后快
        set.playTogether(translationAnimator, scaleAnimator);//一起执行
        //set.playSequentially(translationAnimator,scaleAnimator);//先后执行
        set.start();

        //下落完之后就上抛了.需要监听动画执行完毕
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mShapeView.exchange();//改变形状
                //上抛
                startUpAnimator();
                //实现旋转

            }


        });
    }

    /**
     * 执行上抛动画
     */
    private void startUpAnimator() {
        //动画作用在谁的身上
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat
                (mShapeView, "translationY", mTranslationDistance, 0);
        //  translationAnimator.setDuration(ANIMATOR_DURATION);
        // translationAnimator.start();
        //配合中间阴影缩小
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat
                (mView, "scaleX", 0.3f, 1f);
        // scaleAnimator.setDuration(ANIMATOR_DURATION);
        // scaleAnimator.start();

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new DecelerateInterpolator());
        set.playTogether(translationAnimator, scaleAnimator);//一起执行
        //set.playSequentially(translationAnimator,scaleAnimator);//先后执行
        set.setDuration(ANIMATOR_DURATION);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startFallAnimator();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                //开始旋转
                startRotationAnimator();
            }
        });
        set.start();

    }

    //旋转的方法
    private void startRotationAnimator() {

        //动画作用在谁的身上
        ObjectAnimator rotationAnimator = null;
        switch (mShapeView.getCurrentShape()) {
            case Circle:
                return;
            case Square:
                rotationAnimator = ObjectAnimator.ofFloat
                        (mShapeView, "rotation", 0, 180);
                // 180
                break;
            case Triangle:
                rotationAnimator = ObjectAnimator.ofFloat
                        (mShapeView, "rotation", 0, 120);
                //60
                break;
        }
        rotationAnimator.setDuration(ANIMATOR_DURATION);
        rotationAnimator.setInterpolator(new DecelerateInterpolator());
        rotationAnimator.start();


    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}




























