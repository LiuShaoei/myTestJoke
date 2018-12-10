package xst.app.com.essayjoke;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xst.app.com.baselibrary.ioc.ViewById;
import xst.app.com.framelibrary.BaseSkinActivity;

public class TestRecyclerViewActivity extends BaseSkinActivity {

    @ViewById(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    private TestAdapter mTestAdapter;
    private List<TestRecyclerBean> lists = new ArrayList<>();



    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycleview_test;
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        for(int i = 0;i< 10;i++){
            TestRecyclerBean bean = new TestRecyclerBean();
            bean.setId(i);
            bean.setName(i+"我是中国人");
            lists.add(bean);
        }
        mRecyclerView.setAdapter(mTestAdapter= new TestAdapter(this,lists));
        lists.clear();
        for(int i = 0;i< 100;i++){
            TestRecyclerBean bean1 = new TestRecyclerBean();
            if(i%3 == 0){
                bean1.setId(1);
            }else{
                bean1.setId(0);
            }
            bean1.setName(i*2+"我是河南人");
            lists.add(bean1);
        }
        mTestAdapter.setOnClickListener((data,position)->
            Toast.makeText(this,((TestRecyclerBean)data).getName() + position,Toast.LENGTH_SHORT).show()
        );


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {

    }
}
