package lzw.app.com.baselibrary.tag;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuZhaowei on 2018/12/25 0025.
 */
public class TagLayout extends ViewGroup implements ObserverListener{

    private TagAdapter mAdapter;
    private List<List<View>> mChildViews = new ArrayList<>();

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //2.1  onMeasure()指定宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //清空集合
        mChildViews.clear();
        int childCount = getChildCount();
        //获取到宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //高度需要计算
        int height = getPaddingTop() + getPaddingBottom();
        //一行的宽度
        int lineWidth = getPaddingLeft();
        ArrayList<View> childViews = new ArrayList<>();
        mChildViews.add(childViews);
        //子view高度不一致的情况下
        int maxHeight = 0;
        for (int i = 0; i < childCount; i++) {
            //2.1.1for循环测量子view
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE) {
                continue;
            }
            //这段话执行之后就可以获取子view的宽度,因为会调用子view的onMeasure方法
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //margin值, ViewGroup.LayoutParams 没有就用系统的MarginLayoutParams
            ViewGroup.LayoutParams params = childView.getLayoutParams();
            ViewGroup.MarginLayoutParams marginParams;
            //获取view的margin设置参数
            if (params instanceof ViewGroup.MarginLayoutParams) {
                marginParams = (ViewGroup.MarginLayoutParams) params;
            } else {
                //不存在时创建一个新的参数
                //基于View本身原有的布局参数对象
                marginParams = new ViewGroup.MarginLayoutParams(params);
            }
            //什么时候换行,一行不够用的时候,考虑margin
            int w = childView.getMeasuredWidth();
            int mr = marginParams.rightMargin;

            if (lineWidth + (childView.getMeasuredWidth() + marginParams.rightMargin + marginParams.leftMargin) > width) {
                //换行,累加高度 加上一行条目中最大的高度
                height += maxHeight;
                //  height += childView.getMeasuredHeight() + params.bottomMargin + params.topMargin;
                lineWidth = childView.getMeasuredWidth() + marginParams.rightMargin + marginParams.leftMargin;
                childViews = new ArrayList<>();
                mChildViews.add(childViews);
            } else {
                lineWidth += childView.getMeasuredWidth() + marginParams.rightMargin + marginParams.leftMargin;
                maxHeight = Math.max(childView.getMeasuredHeight() + marginParams.bottomMargin + marginParams.topMargin, maxHeight);
            }

            childViews.add(childView);
        }
        height += maxHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 摆放view
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //   int childCount = getChildCount();
        int left, top = getPaddingTop(), right, bottom;
        for (List<View> childViews : mChildViews) {
            left = getPaddingLeft();
            int maxHeight = 0;
            for (View childView : childViews) {
                if (childView.getVisibility() == GONE) {
                    continue;
                }
                ViewGroup.LayoutParams params = childView.getLayoutParams();
                ViewGroup.MarginLayoutParams marginParams = null;
                //获取view的margin设置参数
                if (params instanceof ViewGroup.MarginLayoutParams) {
                    marginParams = (ViewGroup.MarginLayoutParams) params;
                } else {
                    //不存在时创建一个新的参数
                    //基于View本身原有的布局参数对象
                    marginParams = new ViewGroup.MarginLayoutParams(params);
                }

                left += marginParams.leftMargin;
                int childTop = top + marginParams.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = childTop + childView.getMeasuredHeight();

                //摆放
                childView.layout(left, childTop, right, bottom);
                //left叠加
                left += childView.getMeasuredWidth() + marginParams.rightMargin;
                int childHeight = childView.getMeasuredHeight() + marginParams.topMargin + marginParams.bottomMargin;
                maxHeight = Math.max(maxHeight, childHeight);
            }
            //不断的叠加top值
            //MarginLayoutParams params = (MarginLayoutParams) childViews.get(0).getLayoutParams();
            // top += childViews.get(0).getMeasuredHeight() + params.topMargin + params.bottomMargin;
            top += maxHeight;
        }
    }



    public void setAdapter(TagAdapter adapter) {
        if (adapter == null) {
            //对外抛空异常
            throw new NullPointerException("NullPointerExceptionLzw");
        }
        if (adapter != null) {
            adapter.unregisterAll();
        }
        adapter.registerObserver(this);
        //清空所有的子view
        removeAllViews();
        mAdapter = null;
        mAdapter = adapter;
        //获取tag的数量
        int childCount = adapter.getCount();
        for (int i = 0; i < childCount; i++) {
            //通过位置,获取view
            View view = mAdapter.getView(i, this);
            addView(view);
        }
    }

    @Override
    public void observerUpData(int code,int position) {
        Log.i("love",position+"");
        getChildAt(position).setVisibility(GONE);
      //  removeViewAt(position);//这个方法内部调用了requestLayout();和invalidate();
       // requestLayout();
       // invalidate();
    }
}
