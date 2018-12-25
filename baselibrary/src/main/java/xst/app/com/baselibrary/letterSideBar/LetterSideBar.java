package xst.app.com.baselibrary.letterSideBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by LiuZhaowei on 2018/12/24 0024.
 */
public class LetterSideBar extends View {

    private Paint mPaint;//画笔
    //定义26个字母
    private String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    //当前字母的
    private String mCurrentTouchLetter;

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //字体颜色不固定,这里自定义属性
        mPaint.setTextSize(sp2px(12));//这里设置的是像素
        //颜色,默认蓝色
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算宽度 = 左右的padding+字母的宽度
        int textWidth = (int) mPaint.measureText("A");
        int width = getPaddingLeft() + getPaddingRight() + textWidth;
        //高度直接获取
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //自定宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int itemHeight = (getHeight() - getPaddingBottom() - getPaddingTop()) / mLetters.length;
        //画26个字母
        for (int i = 0; i < mLetters.length; i++) {
            //知道每个字母的中心位置   1字母的高度一般,2字母的高度+前面字符的高度
            int letterCenterY = i * itemHeight + itemHeight / 2 + getPaddingTop();
            //基线,基于中心位置
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
            int baseLineY = letterCenterY + dy;
            //x绘制在最中间,整个控件的宽度的一般,减去字母的宽度的一半
            int textWidth = (int) mPaint.measureText(mLetters[i]);
            int x = getWidth() / 2 - textWidth / 2;

            //当前字母要高亮,用两个画笔或者改变颜色,这里最好用两个画笔
            if (mLetters[i].equals(mCurrentTouchLetter)) {
                mPaint.setColor(Color.RED);
                canvas.drawText(mLetters[i], x, baseLineY, mPaint);
            } else {
                mPaint.setColor(Color.BLUE);
                canvas.drawText(mLetters[i], x, baseLineY, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //计算当前触摸的字母,获取当前位置
                float currentMoveY = event.getY();
                //位置== currentMoveY/字母高度,通过位置获取字母
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
                int currentPosition = (int) (currentMoveY / itemHeight);
                if (currentPosition < 0)
                    currentPosition = 0;
                if (currentPosition > mLetters.length - 1)
                    currentPosition = mLetters.length - 1;
                mCurrentTouchLetter = mLetters[currentPosition];

                if (mListener != null) {
                    mListener.touch(mCurrentTouchLetter, true);
                }
                //重新绘制
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //消失非常快,需要延长消失
                postDelayed(()->{
                    if (mListener != null) {
                        mListener.touch(mCurrentTouchLetter, false);
                    }
                },700);
                break;
        }
        return true;
    }

    //触摸回调
    private LetterTouchLetterListener mListener;

    public void setListener(LetterTouchLetterListener mListener) {
        this.mListener = mListener;
    }

    public interface LetterTouchLetterListener {
        void touch(CharSequence letter, boolean isTouch);
    }

    /**
     * sp转px
     *
     * @param sp
     * @return
     */
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
