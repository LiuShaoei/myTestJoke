package xst.app.com.essayjoke.city_select;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiuZhaowei on 2018/12/29 0029.
 */
public  class HotCityViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;//存储list_Item的View
    private View mConvertView;//list_Item

    public HotCityViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    //获取实例
    public static HotCityViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        HotCityViewHolder holder = new HotCityViewHolder(itemView);
        return holder;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}