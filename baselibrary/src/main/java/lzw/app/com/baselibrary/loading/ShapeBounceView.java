package lzw.app.com.baselibrary.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ShapeBounceView extends View {
    private Shape mCurrentShape = Shape.Circle;
    Paint mPaint;
    private Path mPath;


    public ShapeBounceView(Context context) {
        this(context, null);
    }

    public ShapeBounceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeBounceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //只保证是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    public Shape getCurrentShape(){
        return mCurrentShape;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mCurrentShape) {
            case Circle:
                //画圆
                int center = getWidth() / 2;
                mPaint.setColor(Color.parseColor("#2ECCFA"));
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case Square:
                //画正方形
                mPaint.setColor(Color.parseColor("#F7D358"));
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                break;
            case Triangle:
                mPaint.setColor(Color.parseColor("#6E6E6E"));
                //画路径
                if (mPath == null) {
                    mPath = new Path();
                    mPath.moveTo(getWidth() / 2, 0);
                    mPath.lineTo(0, (float) ((getWidth() / 2) * Math.sqrt(3)));
                    mPath.lineTo(getWidth(), (float) ((getWidth() / 2) * Math.sqrt(3)));
                    mPath.close();//关闭路径
                }
                canvas.drawPath(mPath, mPaint);
                break;
        }
    }

    public void exchange() {
        switch (mCurrentShape) {
            case Circle:
                mCurrentShape = Shape.Square;
                break;
            case Square:
                mCurrentShape = Shape.Triangle;
                break;
            case Triangle:
                mCurrentShape = Shape.Circle;
                break;
        }
        invalidate();
    }

    public enum Shape {
        Circle, Square, Triangle
    }
}
