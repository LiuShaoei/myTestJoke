package lzw.app.com.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by LiuZhaowei on 2018/12/9 0009.
 * 资源类
 */
public class SkinResource {

    private Resources mSkinResources;
    private String mPackageName;

    public SkinResource(Context context, String skinPath) {
        //读取本地的一个.skin里面的资源
        Resources superRes = context.getResources();
        //穿件AssetManager
        AssetManager asset = null;
        try {
            asset = AssetManager.class.newInstance();
            //添加本地下载好的资源皮肤 Native层c和c++
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            //method.setAccessible(true);设置可以访问私有方法
            method.invoke(asset, skinPath);
            mSkinResources = new Resources(asset, superRes.getDisplayMetrics(), superRes.getConfiguration());

            //获取包名
            mPackageName = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Drawable getDrawableByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "drawable", mPackageName);
            Drawable drawable = mSkinResources.getDrawable(resId);
            return drawable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ColorStateList getColorByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "color", mPackageName);
            ColorStateList color = mSkinResources.getColorStateList(resId);
            return color;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
