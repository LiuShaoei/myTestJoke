package xst.app.com.essayjoke;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xst.app.com.baselibrary.ioc.OnClick;
import xst.app.com.baselibrary.ioc.ViewById;
import xst.app.com.baselibrary.tag.TagAdapter;
import xst.app.com.baselibrary.tag.TagLayout;
import xst.app.com.essayjoke.observer.ObserverManager;
import xst.app.com.framelibrary.BaseSkinActivity;

/**
 * Created by LiuZhaowei on 2018/12/25 0025.
 */
public class TagActivity extends BaseSkinActivity {
    @ViewById(R.id.tag_view)
    private TagLayout mTagLayout;
    private int mPosition;

    private List<String> mItems;
    private TagAdapter mTagAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tag;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mItems = new ArrayList<>();
        mItems.add("1");
        mItems.add("22");
        mItems.add("333");
        mItems.add("4444");
        mItems.add("55555");
        mItems.add("666666");
        mItems.add("7777777");
        mItems.add("88888888");
        mItems.add("999999999");
        mItems.add("0000000000");

        mTagLayout.setAdapter(mTagAdapter = new TagAdapter() {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                TextView textView = (TextView) LayoutInflater.from(TagActivity.this).inflate(R.layout.item_tag, parent, false);
                textView.setText(mItems.get(position));
                textView.setOnClickListener(v -> {

                    if (textView.isActivated()) {
                        textView.setActivated(false);
                        mTagAdapter.notifyObserver(1,position);
                    } else {

                        textView.setActivated(true);
                    }
                });
                return textView;
            }
        });


    }

    @OnClick(R.id.test_unregister)
    public void onClick(View view){

     //   mTagAdapter.notifyObserver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTagAdapter.unregisterObserver(mTagLayout);
    }

    @Override
    protected void initTitle() {

    }
}
