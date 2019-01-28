package lzw.app.com.essayjoke;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lzw.app.com.baselibrary.banner.BannerAdapter;
import lzw.app.com.baselibrary.banner.BannerView;
import lzw.app.com.baselibrary.banner.BannerViewPager;
import lzw.app.com.baselibrary.ioc.ViewById;
import lzw.app.com.essayjoke.bean.BannerBean;
import lzw.app.com.framelibrary.BaseSkinActivity;
import lzw.app.com.framelibrary.DefaultNavigationBar;

/**
 * Created by LiuZhaowei on 2018/12/14 0014.
 */
public class BannerTestActivity extends BaseSkinActivity {
    @ViewById(R.id.banner_viewpager)
    private BannerViewPager mBannerViewPager;
    private BannerAdapter mBannerAdapter;
    private List<BannerBean> banners = new ArrayList<>();
    private String[] urls = {"https://img.hcjinfu.com/1539674713744.jpg",
                            "https://img.hcjinfu.com/1541469764234.jpg",
                            "https://img.hcjinfu.com/1539674587753.png"};
    @ViewById(R.id.banner_desc)
    private BannerView mBannerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banner_test;
    }

    @Override
    protected void initView() {
        for (int i = 0; i < urls.length; i++) {
            BannerBean bean = new BannerBean();
            bean.setImageUrl(urls[i]);
            bean.setTitle("我是第"+i+"个广告位");
            banners.add(bean);
        }
    }

    @Override
    protected void initData() {
        mBannerViewPager.setAdapter(mBannerAdapter = new BannerAdapter() {
            @Override
            public View getView(int position) {
                ImageView banner = new ImageView(BannerTestActivity.this);
                // GlideApp.with(BannerTestActivity.this).load(banners.get(0)).into(banner);
                Picasso.get().load(banners.get(position).imageUrl).into(banner);
                return banner;
            }

            @Override
            public int getCount() {
                return banners.size();
            }

            @Override
            public String getBannerDesc(int mCurrentPosition) {
                return banners.get(mCurrentPosition).getTitle();
            }
        });
        //开始滚动
        mBannerViewPager.setScrollerDuration(1050);
        mBannerViewPager.startRoll();

        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position) {
                ImageView banner = new ImageView(BannerTestActivity.this);
                // GlideApp.with(BannerTestActivity.this).load(banners.get(0)).into(banner);
                Picasso.get().load(banners.get(position).imageUrl).into(banner);
                return banner;
            }

            @Override
            public int getCount() {
                return banners.size();
            }
            @Override
            public String getBannerDesc(int mCurrentPosition) {
                return banners.get(mCurrentPosition).getTitle();
            }
        });
        mBannerView.startRoll();
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("自定义Banner").builder();
    }
}
