package lzw.app.com.baselibrary.commonAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiuZhaowei on 2018/12/11 0011.
 * 实现添加头部和底部 继承使用
 */
public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //代表数据列表的adapter
    private RecyclerView.Adapter mAdapter;
    //头部和底部的集合,使用map集合进行标识,key是int value 是object

    private SparseArray<View> mHeaders, mFooters;
    //方法添加头部和底部
    private static int BASE_HEADER_KEY = 1000000;
    private static int BASE_FOOTER_KEY = 2000000;


    public WrapRecyclerAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        mHeaders = new SparseArray();
        mFooters = new SparseArray();
    }

    @Override
    public int getItemViewType(int position) {
        //根据 position --> viewType
        int numHeaders = mHeaders.size();
        if (position < numHeaders) {
            return mHeaders.keyAt(position);
        }

        final int adjPosition = position - numHeaders;
        int adaptionCount = 0;
        if (mAdapter != null) {
            adaptionCount = mAdapter.getItemCount();
            if (adjPosition < adaptionCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        return mFooters.keyAt(adjPosition - adaptionCount);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //怎么区分头底等,可能是头部, 底部,中间
        if (mHeaders.indexOfKey(viewType) >= 0) {
            //头部
            return createFooterHeaderViewHolder(mHeaders.get(viewType));
        } else if (mFooters.indexOfKey(viewType) >= 0) {
            //底部
            return createFooterHeaderViewHolder(mFooters.get(viewType));
        }
        //列表
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    /**
     * 创建头部和底部的viewHolder
     *
     * @param view
     * @return
     */
    private RecyclerView.ViewHolder createFooterHeaderViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int numHeaders = mHeaders.size();
        //头部和底部都不用绑定数据
        if (position < numHeaders) {
            return;
        }
        final int adjPosition = position - numHeaders;
        int adaptionCount = 0;
        if (mAdapter != null) {
            adaptionCount = mAdapter.getItemCount();
            if (adjPosition < adaptionCount) {
                mAdapter.onBindViewHolder(viewHolder, adjPosition);
            }
        }
    }

    @Override
    public int getItemCount() {
        //头底+recyclerView
        return mAdapter.getItemCount() + mHeaders.size() + mFooters.size();
    }

    public void addHeaderView(View view) {
        if (mHeaders.indexOfValue(view) == -1) {
            //集合没有就添加进去,不要重复添加
            mHeaders.put(BASE_HEADER_KEY++, view);
            notifyDataSetChanged();
        }
    }

    public void addFooterView(View view) {
        if (mFooters.indexOfValue(view) == -1) {
            //集合没有就添加进去,不要重复添加
            mFooters.put(BASE_FOOTER_KEY++, view);
            notifyDataSetChanged();
        }
    }

    public void removeHeaderView(View view) {
        if (mHeaders.indexOfValue(view) >= 0) {
            mHeaders.removeAt(mHeaders.indexOfValue(view));
            notifyDataSetChanged();
        }
    }

    public void removeFooterView(View view) {
        if (mFooters.indexOfValue(view) >= 0) {
            mFooters.removeAt(mFooters.indexOfValue(view));
            notifyDataSetChanged();
        }
    }
}
