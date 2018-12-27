package xst.app.com.baselibrary.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;

import xst.app.com.baselibrary.R;

/**
 * Created by LiuZhaowei on 2018/12/27 0027.
 * 模仿雅虎新闻的loading
 */
public class LoadingRotationView extends View {
    //旋转动画执行的时间
    private final long ROTATION_ANIMATION_TIME = 3000;
    //当前大圆旋转的角度
    private float mCurrentRotationAngle = 0f;
    //小圆的颜色列表
    private int[] mCircleColors;
    //大圆里面包含很多小圆的半径 = 整宽的1/3
    private float mRotationRadius;
    private boolean mInitParams = false;//只初始化一次
    //小圆的半径
    private float mCircleRadius;
    //绘制所有效果的画笔
    private Paint mPaint;
    //这是画图的基准点,也是屏幕的中心点
    private float mCenterX;
    private float mCenterY;
    private LoadingState mLoadingState;//代表当前状态说话动画
    //当前空心圆的半径
    private float mHoleRadius = 0f;
    private float mDiagonalDist;//扩散开圆半径是手机屏幕对角线的一般

    private final int mSplashColor = Color.WHITE;

    public LoadingRotationView(Context context) {
        this(context, null);
    }

    public LoadingRotationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingRotationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取颜色列表
        mCircleColors = getContext().getResources().getIntArray(R.array.splash_circle_colors);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mInitParams) {
            initParams();
        }
        if (mLoadingState == null) {
            mLoadingState = new RotationState();
        }
        mLoadingState.draw(canvas);
    }

    /**
     * 圆消失
     */
    public void disappear() {
        //开始聚合动画,关闭旋转动画
        if (mLoadingState instanceof RotationState) {
            RotationState rotationState = (RotationState) mLoadingState;
            rotationState.cancel();
        }
        mLoadingState = new MergeState();
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        mRotationRadius = getMeasuredWidth() / 4;
        mCircleRadius = mRotationRadius / 8;
        mInitParams = true;
        mPaint = new Paint();
        mPaint.setDither(true);//防抖动
        mPaint.setAntiAlias(true);//抗锯齿
        mCenterY = getMeasuredHeight() / 2;
        mCenterX = getMeasuredWidth() / 2;
        mDiagonalDist = (float) Math.sqrt(mCenterX * mCenterX + mCenterY * mCenterY);
    }


    public abstract class LoadingState {
        public abstract void draw(Canvas canvas);
    }

    /**
     * 旋转动画
     */
    public class RotationState extends LoadingState {

        private ValueAnimator mAnimator;//旋转是0 ->360

        public RotationState() {
            //搞一个变量不断的去改变,采用属性动画
            if (mAnimator == null) {
                mAnimator = ObjectAnimator.ofFloat(0f, 2 * (float) Math.PI);
                mAnimator.setDuration(ROTATION_ANIMATION_TIME);
                mAnimator.addUpdateListener(animation -> {
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();
                    //重新绘制
                    invalidate();
                });
                mAnimator.setInterpolator(new LinearInterpolator());//设置差值器
                //不断反复执行
                mAnimator.setRepeatCount(-1);
                mAnimator.start();
            }

        }

        @Override
        public void draw(Canvas canvas) {

            canvas.drawColor(mSplashColor);//设置圆的背景
            //画多个圆
            double percentAngle = Math.PI * 2 / mCircleColors.length;//初始角度,用整个角度/有多少个圆
            for (int i = 0; i < mCircleColors.length; i++) {
                //当前的角度= 初始角度 + 旋转角度
                mPaint.setColor(mCircleColors[i]);
                double currentAngle = percentAngle * i + mCurrentRotationAngle;
                int cx = (int) (mCenterX + mRotationRadius * Math.cos(currentAngle));
                int cy = (int) (mCenterY + mRotationRadius * Math.sin(currentAngle));
                canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
            }
        }

        /**
         * 取消动画
         */
        public void cancel() {
            mAnimator.cancel();
        }
    }

    /**
     * 聚合动画
     */
    public class MergeState extends LoadingState {
        private ValueAnimator mAnimator;//聚合是是大圆的半径从当前->0;

        public MergeState() {
            //搞一个变量不断的去改变,采用属性动画
            if (mAnimator == null) {
                mAnimator = ObjectAnimator.ofFloat(mRotationRadius, 0);
                mAnimator.setDuration(ROTATION_ANIMATION_TIME / 2);
                mAnimator.addUpdateListener(animation -> {
                    mRotationRadius = (float) animation.getAnimatedValue();
                    //重新绘制
                    invalidate();
                });
                mAnimator.setInterpolator(new AnticipateInterpolator(3f));//设置差值器
                //聚合完毕,画展开
                mAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoadingState = new ExpendState();
                    }
                });
                //不断反复执行
                mAnimator.start();
            }
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(mSplashColor);//设置圆的背景
            //画多个圆
            double percentAngle = Math.PI * 2 / mCircleColors.length;//初始角度,用整个角度/有多少个圆
            for (int i = 0; i < mCircleColors.length; i++) {
                //当前的角度= 初始角度 + 旋转角度
                mPaint.setColor(mCircleColors[i]);
                double currentAngle = percentAngle * i + mCurrentRotationAngle;
                int cx = (int) (mCenterX + mRotationRadius * Math.cos(currentAngle));
                int cy = (int) (mCenterY + mRotationRadius * Math.sin(currentAngle));
                canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
            }
        }

    }

    /**
     * 展开动画
     */
    public class ExpendState extends LoadingState {
        private ValueAnimator mAnimator;//聚合是是大圆的半径从当前->0;

        public ExpendState() {
            //搞一个变量不断的去改变,采用属性动画
            if (mAnimator == null) {
                mAnimator = ObjectAnimator.ofFloat(0, mDiagonalDist);
                mAnimator.setDuration(ROTATION_ANIMATION_TIME / 2);
                mAnimator.addUpdateListener(animation -> {
                    mHoleRadius = (float) animation.getAnimatedValue();
                    //重新绘制
                    invalidate();
                });

                mAnimator.start();
            }
        }

        @Override
        public void draw(Canvas canvas) {
            //画笔的宽度
            float strokeWidth = mDiagonalDist - mHoleRadius;
            mPaint.setColor(mSplashColor);
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setStyle(Paint.Style.STROKE);
            //绘制一个圆
            canvas.drawCircle(mCenterX, mCenterY, mHoleRadius + strokeWidth / 2, mPaint);
        }
    }
}
