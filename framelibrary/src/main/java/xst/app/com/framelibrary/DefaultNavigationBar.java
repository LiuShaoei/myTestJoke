package xst.app.com.framelibrary;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import xst.app.com.baselibrary.navigationbar.AbsNavigationBar;

/**
 * Created by LiuZhaowei on 2018/12/7 0007.
 */
public class DefaultNavigationBar extends AbsNavigationBar <DefaultNavigationBar.Builder.DefaultNavigationParams>{

    public DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationParams mParams) {
        super(mParams);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
        //绑定效果
        setText(R.id.title,getParams().mTitle);
        setText(R.id.right_title,getParams().mRightText);
        setText(R.id.left_title,getParams().mLeftText);
        setOnClickListener(R.id.right_title,getParams().mRightClickListener);
        //左边写一个默认的, finish();
    }



    public static class Builder extends AbsNavigationBar.Builder {

        DefaultNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }
        public Builder(Context context) {
            super(context,null);
            P = new DefaultNavigationParams(context, null);
        }


        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(P);
            return navigationBar;
        }

        // 1.设置效果
        //设置中间的title
        public DefaultNavigationBar.Builder setTitle(String title){
            P.mTitle = title;
            return this;
        }
        //设置右边的文字
        public DefaultNavigationBar.Builder setRightText(String rightText){
            P.mRightText = rightText;
            return this;
        }
        //设置左边的文字
        public DefaultNavigationBar.Builder setLefText(String leftText){
            P.mLeftText = leftText;
            return this;
        }
        //设置右边的图片
        public DefaultNavigationBar.Builder setRightIcon(int rightRes){
            P.mRightIcon = rightRes;
            return this;
        }
        //设置右边的点击事件
        public DefaultNavigationBar.Builder setRightClickListener(View.OnClickListener rightListener){
            P.mRightClickListener  = rightListener;
            return this;
        }
        public static class DefaultNavigationParams extends
                AbsNavigationBar.Builder.AbsNavigationParams {
            //2.所有效果
            public String mRightText;
            public int mRightIcon;
            public String mTitle;
            public View.OnClickListener mRightClickListener  = (v) -> ((Activity) (mContext)).finish();
            public String mLeftText;


            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
