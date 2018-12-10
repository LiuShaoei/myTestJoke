package xst.app.com.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by LiuZhaowei on 2018/12/9 0009.
 */
public class SkinView {
    private View mView;
    private List<SkinAttr> mSkinAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mSkinAttrs = skinAttrs;
    }

    public void skin() {
        for (SkinAttr attr : mSkinAttrs) {
            attr.skin(mView);
        }
    }
}
