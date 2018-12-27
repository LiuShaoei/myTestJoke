package xst.app.com.essayjoke;

import xst.app.com.baselibrary.ioc.ViewById;
import xst.app.com.baselibrary.loading.LoadingRotationView;
import xst.app.com.framelibrary.BaseSkinActivity;

/**
 * Created by LiuZhaowei on 2018/12/27 0027.
 */
public class LoadingViewActivity extends BaseSkinActivity {
    @ViewById(R.id.loading)
    private LoadingRotationView mLoadingView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading_view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //模拟后台数据
        new android.os.Handler().postDelayed(() ->
                        mLoadingView.disappear()
                , 3000);
    }

    @Override
    protected void initTitle() {

    }
}
