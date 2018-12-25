package xst.app.com.baselibrary.commonAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LiuZhaowei on 2018/12/10 0010.
 * 定制viewHolder,封装功能对外暴露
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;//key是一个integer

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        //这里还是会多次调用 需要对已有的view做一个缓存 减少查找次数
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    //通用的功能,进行封装,设置文本,设置条目点击事件,设置图片
    public ViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImage(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    public ViewHolder setBackgroundImage(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public ViewHolder setVisible(int viewId, int visible) {
        getView(viewId).setVisibility(visible);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundColor(resId);
        return this;
    }

    //图片的处理问题,路径问题用到第三方的.

    public ViewHolder setImagePath(int viewId, HolderImageLoader imageLoader) {
        ImageView imageView = getView(viewId);
        imageLoader.loadImage(imageView, imageLoader.getPath());
        return this;
    }

    //实现无关第三方
    public abstract static class HolderImageLoader {
        private String mPath;

        public HolderImageLoader(String path) {
            this.mPath = path;
        }

        public String getPath() {
            return mPath;
        }

        /**
         * 需要复写这个加载图片
         *
         * @param imageView
         * @param path
         */
        public abstract void loadImage(ImageView imageView, String path);
    }
}
