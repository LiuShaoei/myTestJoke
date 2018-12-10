package xst.app.com.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xst.app.com.framelibrary.skin.SkinManager;
import xst.app.com.framelibrary.skin.SkinResource;

/**
 * Created by LiuZhaowei on 2018/12/9 0009.
 */
public enum SkinType {
    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            ColorStateList color = skinResource.getColorByName(resName);
            if (color == null) {
                return;
            }
            TextView textView = (TextView) view;
            textView.setTextColor(color);

        }
    },
    BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {
            //背景可能是图片,也可能是颜色
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable != null) {
                ImageView imageView = (ImageView) view;
                imageView.setBackground(drawable);
                return;
            }
            // 拿不到可能是颜色
            ColorStateList color = skinResource.getColorByName(resName);
            if (color != null) {
                view.setBackgroundColor(color.getDefaultColor());
            }

        }
    },
    SRC("src") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable != null) {
                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable(drawable);
                return;
            }
        }
    };

    protected SkinResource getSkinResource() {
        return SkinManager.getInstance().getSkinResource();
    }

    //会根据名字调用对应的方法
    private String mResName;

    SkinType(String resName) {
        this.mResName = resName;
    }

    public abstract void skin(View view, String resName);

    public String getResName() {
        return mResName;
    }
}
