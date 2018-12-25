package xst.app.com.baselibrary.banner;

        import android.view.View;

/**
 * Created by LiuZhaowei on 2018/12/14 0014.
 */
public abstract class BannerAdapter  {
    //更具位置获取viewPager里面的子view
    public abstract View getView(int position);
    //获取轮播的数量
    public abstract int getCount();
    //根据位置获取广告位描述
    public  String getBannerDesc(int mCurrentPosition){
        return "";
    }
}
