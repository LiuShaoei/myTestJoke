package lzw.app.com.essayjoke;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lzw.app.com.baselibrary.ioc.ViewById;
import lzw.app.com.baselibrary.view.WrapRecyclerView;
import lzw.app.com.essayjoke.adapter.TestAdapter;
import lzw.app.com.essayjoke.bean.TestRecyclerBean;
import lzw.app.com.framelibrary.BaseSkinActivity;
import lzw.app.com.framelibrary.DefaultNavigationBar;

public class TestRecyclerViewActivity extends BaseSkinActivity {

    private static final String TAG = "tag";
    @ViewById(R.id.recycler_view)
    private WrapRecyclerView mRecyclerView;
    private List<TestRecyclerBean> lists = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycleview_test;
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        for (int i = 0; i <6; i++) {
            TestRecyclerBean bean = new TestRecyclerBean();
            bean.setId(i);
            bean.setName(i + "我是中国人");
            lists.add(bean);
        }
        TestAdapter adapter = new TestAdapter(this, lists);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickListener(((position) -> {
            Toast.makeText(this, "删除" + lists.get(position).getId(), Toast.LENGTH_SHORT).show();
            lists.remove(position);
            adapter.notifyDataSetChanged();
        }));


        final View header = LayoutInflater.from(this).inflate(R.layout.recycler_header_view, mRecyclerView, false);
        mRecyclerView.addHeaderView(header);

        header.setOnClickListener((v -> mRecyclerView.removeHeaderView(v)));
        final View footer = LayoutInflater.from(this).inflate(R.layout.recycler_footer_view, mRecyclerView, false);

        mRecyclerView.addFooterView(footer);


        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int tag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                int  tags = 0;
//                if(recyclerView.getLayoutManager() instanceof GridLayoutManager){
//                    tags = ItemTouchHelper.UP | ItemTouchHelper.DOWN|ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT;
//                }else{
//                    tags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//                }
                return makeMovementFlags(tags,tag);
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                //状态的改变,拖动状态.
                if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                    viewHolder.itemView.setBackgroundColor(Color.GREEN);
                }

            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                //动画执行完毕
                //原来是什么背景,现在还是要设置什么背景
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#D81B60"));
                //侧滑和拖动都是使用ViewCompat这个类
                ViewCompat.setTranslationX(viewHolder.itemView,0);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //侧滑执行完毕
                //在拖动的过程中不断的回调,不断的替换位置,找到当前的位置,替换原来的位置
                //获取原来的位置
                int fromPosition = viewHolder.getAdapterPosition();
                //得到目标的位置
                int targetPosition = target.getAdapterPosition();

                if(fromPosition > targetPosition){
                    for(int i  = fromPosition; i< targetPosition;i++){
                        Collections.swap(lists,i,i+1);//改变实际的的数据集
                    }
                }else{
                    for(int j = fromPosition; j> targetPosition;j--){
                        Collections.swap(lists,j,j-1);//改变实际数据集
                    }
                }
                //替换 这个时候替换的只是位置,数据并没有变化接下来我们要改变数据
                adapter.notifyItemMoved(fromPosition,targetPosition);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int currentPosition = viewHolder.getAdapterPosition();
                adapter.notifyItemRemoved(currentPosition);
                Log.e(TAG,"位置"+currentPosition);
                lists.remove(currentPosition);
            }
        });
        helper.attachToRecyclerView(mRecyclerView);

//        mRecyclerView.setAdapter(mTestAdapter= new TestAdapter(this,lists));
//        lists.clear();
//        for(int i = 0;i< 100;i++){
//            TestRecyclerBean bean1 = new TestRecyclerBean();
//            if(i%3 == 0){
//                bean1.setId(1);
//            }else{
//                bean1.setId(0);
//            }
//            bean1.setName(i*2+"我是河南人");
//            lists.add(bean1);
//        }
//        mTestAdapter.setOnClickListener((data,position)->
//            Toast.makeText(this,((TestRecyclerBean)data).getName() + position,Toast.LENGTH_SHORT).show()
//        );


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("万能的RecyclerView").builder();
    }
}
