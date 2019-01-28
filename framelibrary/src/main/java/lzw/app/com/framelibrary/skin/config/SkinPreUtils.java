package lzw.app.com.framelibrary.skin.config;

import android.content.Context;

import static lzw.app.com.framelibrary.skin.config.SkinConfig.SKIN_CONFIG_NAME;

/**
 * Created by LiuZhaowei on 2018/12/9 0009.
 */
public class SkinPreUtils {


    private static SkinPreUtils mInstance;
    private Context mContext;

    public SkinPreUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static SkinPreUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SkinPreUtils.class) {
                if (mInstance == null) {
                    mInstance = new SkinPreUtils(context);
                }
            }
        }
        return mInstance;
    }

    public void saveSkinPath(String skinPath) {
        mContext.getSharedPreferences(SkinConfig.SKIN_CONFIG_NAME, Context.MODE_PRIVATE).edit()
                .putString(SkinConfig.SKIN_PATH_NAME, skinPath).commit();
    }

    public String getSkinPath() {
        return mContext.getSharedPreferences(SKIN_CONFIG_NAME, Context.MODE_PRIVATE)
                .getString(SkinConfig.SKIN_PATH_NAME, "");
    }

    public void clearSkinPath() {
        mContext.getSharedPreferences(SkinConfig.SKIN_CONFIG_NAME, Context.MODE_PRIVATE).edit()
                .putString(SkinConfig.SKIN_PATH_NAME, "").commit();
    }
}
