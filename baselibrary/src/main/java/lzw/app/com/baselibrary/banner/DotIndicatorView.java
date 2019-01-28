package lzw.app.com.baselibrary.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuZhaowei on 2018/12/14 0014.
 */
public class DotIndicatorView extends View {
    private Drawable mDrawable;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable != null) {
            //  mDrawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
            //  mDrawable.draw(canvas);
            //画圆
            Bitmap bitmap = drawableToBitmap(mDrawable);
            Bitmap circleBitmap = getCircleBitmap(bitmap);
            //把圆形的bitmap绘制到画布上
            canvas.drawBitmap(circleBitmap, 0, 0, null);
        }

    }

    //把bitmap变为圆的
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        //创建一个bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);//防止抖动
        canvas.drawCircle(getMeasuredWidth() / 2,
                getMeasuredHeight() / 2, getMeasuredWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //再把原来的bitmap绘制到新的圆上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        //取圆和bitmap的交集

        return circleBitmap;
    }

    //从drawable中得到bitmap
    private Bitmap drawableToBitmap(Drawable drawable) {
        //如果是bitmap类型
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        //其他类型
        //创建一个空的drawable
        Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //创建一个画布
        Canvas canvas = new Canvas(outBitmap);
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas);
        return outBitmap;
    }

    //5.设置Drawable
    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        invalidate();//重新绘制当前的view
    }
}
