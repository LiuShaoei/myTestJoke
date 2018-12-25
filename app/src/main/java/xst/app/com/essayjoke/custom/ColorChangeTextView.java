package xst.app.com.essayjoke.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import xst.app.com.essayjoke.R;


/**
 * Created by LiuZhaowei on 2018/12/12 0012.
 * 字体随着滑动颜色变化
 */
public class ColorChangeTextView extends AppCompatTextView {

    private Paint mOriginPaint, mChangePaint;//变色和不变色的画笔

    private float mCurrentProgress = 0.0f;

    private int mOriginColor = Color.GRAY;
    private int mChangeColor = Color.BLUE;
    //实现不同的朝向
    private Direction mDirection = Direction.LEFT_TO_RIGHT;



    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    public ColorChangeTextView(Context context) {
        this(context, null);
    }

    public ColorChangeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorChangeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorChangeTextView);
      //  mOriginColor = array.getColor(R.styleable.ColorChangeTextView_originColor, mOriginColor);
        //mChangeColor = array.getColor(R.styleable.ColorChangeTextView_changeColor, mChangeColor);

        mChangePaint = getPaintByColor(mChangeColor);
        mOriginPaint = getPaintByColor(mOriginColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //super.onDraw(canvas);系统的注释掉
        //根据滑动的进度,把中间值算出来
        int middle = (int) (mCurrentProgress * getWidth());// -->
        if (mDirection == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, mOriginPaint, middle, getWidth());
            drawText(canvas, mChangePaint, 0, middle);
        } else {
            drawText(canvas, mChangePaint, getWidth() - middle, getWidth());
            drawText(canvas, mOriginPaint, 0, getWidth() - middle);
        }
    }

    /**
     * 绘制颜色
     *
     * @param canvas
     * @param paint
     * @param start
     * @param end
     */
    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();//保存画布
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);//裁剪区域
        //canvas.clipRect(0,0,80,getHeight());//裁剪区域
        //自己来画实现
        String text = getText().toString().trim();
        //获取字体的宽度
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;//字体宽度,宽度的一半减去文字的一半就是文字的起始位置
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        //基线
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, paint);
        //通过不断的改变裁剪的值
        canvas.restore();
    }

    /**
     * 设置朝向
     *
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    /**
     * 设置进度
     *
     * @param currentProgress
     */
    public void setCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        invalidate();
    }


    /**
     * 根据颜色获取画笔
     *
     * @param color
     * @return
     */
    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        //设置颜色
        paint.setColor(color);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置防抖动
        paint.setDither(true);
        //设置字体大小,就是TextView的字体大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    public void setChangeColor(int red) {
        mChangePaint.setColor(red);
    }
    public void setTextSize(int size) {
        mChangePaint.setTextSize(size);
    }
    public void setNOChangeColor(int red) {
        mOriginColor = red;
    }
}
