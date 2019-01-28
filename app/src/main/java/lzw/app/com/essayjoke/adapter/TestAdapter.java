package lzw.app.com.essayjoke.adapter;

import android.content.Context;

import java.util.List;

import lzw.app.com.baselibrary.commonAdapter.RecyclerCommonAdapter;
import lzw.app.com.baselibrary.commonAdapter.ViewHolder;
import lzw.app.com.essayjoke.R;
import lzw.app.com.essayjoke.bean.TestRecyclerBean;

/**
 * Created by LiuZhaowei on 2018/12/10 0010.
 */
public class TestAdapter extends RecyclerCommonAdapter<TestRecyclerBean> {

    public TestAdapter(Context context, List list) {
        //单布局
        //  super(context, list,R.layout.recycler_view_right_test);
        super(context, list, (item) -> {
            if (((TestRecyclerBean) item).getId() == 1) {
                return R.layout.recycler_view_right_test;
            } else {
                return R.layout.recycler_view_left_test;
            }
        });
    }

    @Override
    protected void convert(ViewHolder viewHolder, TestRecyclerBean bean, int position) {
        viewHolder.setText(R.id.text_tv, bean.getName());


        // viewHolder.setImagePath(R.id.image_img, new ImageLoader("http://t11.baidu.com/it/u=2871056914,1828008003&fm=173&app=49&f=JPEG?w=600&h=394&s=2DCA722301FBB3846D17E49E0100A081"));
    }
}
