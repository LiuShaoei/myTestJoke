package xst.app.com.framelibrary.skin.attr;

import android.view.View;

/**
 * Created by LiuZhaowei on 2018/12/9 0009.
 */
public class SkinAttr {
    public SkinAttr(String mResName, SkinType mType) {
        this.mResName = mResName;
        this.mType = mType;
    }

    private String mResName;
    private SkinType mType;

    public void skin(View view) {
        mType.skin(view, mResName);
    }
}
