package lzw.app.com.baselibrary.bubble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by LiuZhaowei on 2018/12/27 0027.
 */
public class MessageBubble extends View {

    private PointF mFixationPoint;
    private PointF mDragPoint;
    private int mDragRadius = 10;//拖拽圆的半径
    private Paint mPaint;//画笔
    private int mFixationRadius;
    private int mFixationRadiusMax = 10;//固定圆的半径
    private int mFixationRadiusMin = 2;

    public MessageBubble(Context context) {
        this(context, null);
    }

    public MessageBubble(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageBubble(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragRadius = dip2px(mDragRadius);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }


    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画两个圆
        //1.画拖拽圆
        if (mDragPoint == null || mFixationPoint == null) {
            return;
        }
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPaint);
        //2.刚开始固定圆有个初始位置,并且它的半径是随着距离的增大而不断减小
        //2.1计算两个点的距离
        double distance = getPointDistance(mDragPoint, mFixationPoint);
        mFixationRadius = (dip2px((int) (mFixationRadiusMax - distance / 14)));
        if (mFixationRadius > mFixationRadiusMin) {
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius, mPaint);
            canvas.drawPath(getBeizeerPath(), mPaint);
        }

    }

    public Path getBeizeerPath() {
        double distance = getPointDistance(mDragPoint, mFixationPoint);
        mFixationRadius = (int) (mFixationRadiusMax - distance / 14);
        if (mFixationRadius < mFixationRadiusMin) {
            return null;//超过一定距离,贝塞尔和固定圆都不用画了
        }
        Path beizeerPath = new Path();
        //求角
        float dy = mDragPoint.y - mFixationPoint.y;
        float dx = mDragPoint.x - mFixationPoint.y;
        float tanA = dy / dx;
        double arcTanA = Math.atan(tanA);//通过反tan函数得到角a
        //p0
        float p0x = (float) (mFixationPoint.x + mFixationRadius * Math.sin(arcTanA));
        float p0y = (float) (mFixationPoint.y - mFixationRadius * Math.cos(arcTanA));
        //p1
        float p1x = (float) (mDragPoint.x + mDragRadius * Math.sin(arcTanA));
        float p1y = (float) (mDragPoint.y - mDragRadius * Math.cos(arcTanA));
        //p2
        float p2x = (float) (mDragPoint.x - mDragRadius * Math.sin(arcTanA));
        float p2y = (float) (mDragPoint.y + mDragRadius * Math.cos(arcTanA));
        //p3
        float p3x = (float) (mFixationPoint.x - mFixationRadius * Math.sin(arcTanA));
        float p3y = (float) (mFixationPoint.y + mFixationRadius * Math.cos(arcTanA));
        //要去拼装贝塞尔的曲线路径
        beizeerPath.moveTo(p0x, p0y);
        //两个点,第一个点是控制点,第二个是结束点
        //控制点这里取中间,其实黄金分割点是很好的,这里先不用
        PointF controlPoint = getControlPoint();
        beizeerPath.quadTo(controlPoint.x, controlPoint.y, p1x, p1y);
        //画第二天贝塞尔曲线
        beizeerPath.lineTo(p2x, p2y);//连接起来,形成一个闭合的环境
        beizeerPath.quadTo(controlPoint.x, controlPoint.y, p3x, p3y);
        beizeerPath.close();

        return beizeerPath;
    }

    private PointF getControlPoint() {
        return new PointF((mDragPoint.x + mFixationPoint.x) / 2, (mDragPoint.y + mFixationPoint.y) / 2);
    }

    /**
     * 获取两个值得距离
     *
     * @return
     */
    private double getPointDistance(PointF point1, PointF point2) {
        return Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) - (point1.y - point2.y) * (point1.y - point2.y));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //手指按下去要指定当前位置
                float downX = event.getX();
                float downY = event.getY();
                initPoint(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                updateDragPoint(moveX, moveY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();//更新ui.
        return true;//返回true,事件不向下传导
    }

    /**
     * 更新位置
     *
     * @param moveX
     * @param moveY
     */
    private void updateDragPoint(float moveX, float moveY) {
        mDragPoint.x = moveX;
        mDragPoint.y = moveY;
    }

    /**
     * 初始化位置
     *
     * @param downX
     * @param downY
     */
    private void initPoint(float downX, float downY) {
        mFixationPoint = new PointF(downX, downY);
        mDragPoint = new PointF(downX, downY);
    }
}
