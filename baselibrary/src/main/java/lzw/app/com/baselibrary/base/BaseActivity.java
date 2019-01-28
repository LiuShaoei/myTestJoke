package lzw.app.com.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import lzw.app.com.baselibrary.ioc.ViewUtils;

/**
 * Created by LiuZhaowei on 2018/12/6 0006.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        //针对MVC的模式,和MVP有区别
        //设置布局
        if(getLayoutId() != 0){
            setContentView(getLayoutId());
        }
        //一些特定的算法,放在公共父类
        ViewUtils.inject(this);
        //初始化头部
        initTitle();
        //初始化界面
        initView();
        //初始化数据
        initData();


    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initTitle();

    protected void startActivity(Class <?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
