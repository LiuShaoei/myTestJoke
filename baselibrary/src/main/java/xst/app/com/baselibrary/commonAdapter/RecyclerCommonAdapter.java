package xst.app.com.baselibrary.commonAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by LiuZhaowei on 2018/12/10 0010.
 */
public abstract class RecyclerCommonAdapter<DATA> extends RecyclerView.Adapter<ViewHolder> {

    private int mLayoutId;
    //参数通过泛型
    private List<DATA> mData;
    //实例化上下文
    private LayoutInflater mInflater;

    private Context mContext;
    private MultipleTypeSupport mTypeSupport;
    private ItemClickListener mItemClickListener;

    public RecyclerCommonAdapter(Context context, List<DATA> data, int layoutId) {
        mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
        this.mLayoutId = layoutId;
    }

    public RecyclerCommonAdapter(Context context,List<DATA> data,MultipleTypeSupport typeSupport){
        this(context,data,-1);
        this.mTypeSupport = typeSupport;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(mTypeSupport != null){
            mLayoutId = viewType;
        }
        //创建view
        View itemView = mInflater.inflate(mLayoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        if(mTypeSupport != null){
           return mTypeSupport.getLayoutId(mData.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        convert(viewHolder, mData.get(position), position);
        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener((v -> mItemClickListener.setOnItemClick(mData.get(position),position)));
        }
    }

    public void setOnClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    /**
     * 把必要条件传出去
     *
     * @param viewHolder
     * @param data
     * @param position
     */
    protected abstract void convert(ViewHolder viewHolder, DATA data, int position);


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
