package xst.app.com.essayjoke;

import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import xst.app.com.essayjoke.fixbug.FixDexManager;
import xst.app.com.framelibrary.BaseSkinActivity;

/**
 * Created by LiuZhaowei on 2018/12/6 0006.
 */
public class TestActivity extends BaseSkinActivity implements View.OnClickListener {
    private TextView mTextView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        mTextView = findViewById(R.id.test_text);
        mTextView.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initTitle() {

    }

    @Override
    public void onClick(View v) {

    }
}
