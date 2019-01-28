package lzw.app.com.baselibrary.indicator;

import android.view.View;
import android.view.ViewGroup;

/**
 * 指示器的adapter
 * Created by LiuZhaowei on 2019/1/11 0011.
 */
public abstract class IndicatorAdapter {
    //获取总条数
    public abstract int getCount();

    //获取View
    public abstract View getView(int position, ViewGroup parent);

    public void setClickChangeColor(View view, int state) {

    }

    /**
     * 当设置未特殊状态时,回调
     */
    public void setOnPageScroll(View view1,View view2,float positionOffset){

    }

}
