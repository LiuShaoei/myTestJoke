package lzw.app.com.essayjoke.custom;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import lzw.app.com.essayjoke.R;

/**
 * Created by LiuZhaowei on 2018/12/12 0012.
 */
public class QQStepView extends View {

    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mBorderWidth = 80;
    private int mStepTextSize;
    private int mStepTextColor;
    private Paint mOutPaint,mInnerPaint,mTextPaint;
    private int mStepMax = 100;//步数
    private int mCurrentStep = 50;//当前步数

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.分析效果
        //2.确定自定义属性,编写attrs.xml
        //3.在布局中使用
        //4.在自定义view获取自己的属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        array.recycle();

        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setColor(mOuterColor);
        mOutPaint.setStyle(Paint.Style.STROKE);//画笔是实心的


        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStyle(Paint.Style.STROKE);//画笔是实心的

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);//抗juc
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);
        mTextPaint.setStyle(Paint.Style.STROKE);//画笔是实心的

        //7.其他
    }

    //5.onMeasure();
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者在布局文件中可能, wrap_content 宽度和高度不一致
        //获取模式 AT_MOST
        //宽度高度不一致,取最小值,确保是个正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }

    //6.画圆弧,文字
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //拿到中心点
        int centerPoint = getWidth() / 2;
        //拿到半径
        int radius = getWidth() / 2 - mBorderWidth/2 -5;

        //6.1画外圆弧 分析边缘显示不完整,描边有宽度,需要减去
        RectF rectF = new RectF(
                centerPoint - radius,
                centerPoint - radius,
                centerPoint + radius,
                centerPoint + radius);
        canvas.drawArc(rectF, 135, 270, false, mOutPaint);
        //6.2画内圆弧 内圆弧不能写死,要用百分比,是使用者从外面传
        if(mStepMax == 0) return;//防止被x/0
        float sweepAngle = (float)mCurrentStep/mStepMax;
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mInnerPaint);
        //6.3画文字
        String stepText = mCurrentStep + "";
        Rect textBounds =new Rect();
        mTextPaint.getTextBounds(stepText,0,stepText.length(),textBounds);
        int dx = getWidth()/2 - textBounds.width()/2;
        //基线baseLine
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2 + dy;
        canvas.drawText(stepText,dx,baseLine,mTextPaint);
    }
    //动起来
    public synchronized void setStepMax(int stepMax,int currentStep){
        this.mStepMax = stepMax;
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, currentStep);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());//这里是插值器,实现先满后快
        valueAnimator.addUpdateListener((animation -> {
            float step = (float) animation.getAnimatedValue();
            setCurrentStep((int) step);
        }));
        valueAnimator.start();
    }
    private void setCurrentStep(int currentStep){
        this.mCurrentStep = currentStep;
        invalidate();//不断的重新绘制,里面会调用onDraw()方法.
    }
}
