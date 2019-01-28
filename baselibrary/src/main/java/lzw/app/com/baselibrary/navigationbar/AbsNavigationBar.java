package lzw.app.com.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LiuZhaowei on 2018/12/7 0007.
 * 头部的基类
 */
public class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {

    private P mParams;
    private View mNavigationView;

    public AbsNavigationBar(P mParams) {
        this.mParams = mParams;
        createAndBingView();
    }

    public P getParams() {
        return mParams;
    }

    public void setVisible(int viewId, int visible) {
        View view = findViewById(viewId);
        view.setVisibility(visible);
    }

    public void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    public void setLeftIcon(int viewId, int leftRes) {
        ImageView img = findViewById(viewId);
        if (leftRes != 0) {
            img.setImageResource(leftRes);
        }
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {

        View view = findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }

    }

    public <T extends View> T findViewById(int viewId) {
        return (T) mNavigationView.findViewById(viewId);
    }


    /**
     * \
     * 绑定和创建view
     */
    private void createAndBingView() {
        // 1.创建view
        if (mParams.mParent == null) {
            //获取activity的跟布局
            ViewGroup activityRoot = (ViewGroup) ((Activity) (mParams.mContext)).getWindow().getDecorView();
            mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);
        }
        if (mParams.mParent == null) {
            return;
        }
        mNavigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);//传false
        //2.添加
        mParams.mParent.addView(mNavigationView, 0);
        applyView();
    }


    @Override
    public int bindLayoutId() {
        return 0;
    }

    @Override
    public void applyView() {

    }

    public static abstract class Builder {


        public Builder(Context context, ViewGroup parent) {

        }


        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams {
            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }

        }
    }
}
