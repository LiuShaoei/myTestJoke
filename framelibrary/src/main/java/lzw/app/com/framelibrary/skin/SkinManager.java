package lzw.app.com.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lzw.app.com.framelibrary.skin.attr.SkinView;
import lzw.app.com.framelibrary.skin.config.SkinConfig;
import lzw.app.com.framelibrary.skin.config.SkinPreUtils;

/**
 * Created by LiuZhaowei on 2018/12/9 0009.
 * 皮肤管理类
 */
public class SkinManager {
    private static SkinManager mInstance;
    private Context mContext;
    private SkinResource mSkinResource;

    private Map<Activity, List<SkinView>> mSkinViews = new HashMap<>();

    static {
        mInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return mInstance;
    }

    public void init(Context context) {

        this.mContext = context.getApplicationContext();
        //每一次打开应用都会到这里来,防止皮肤被人以删除 ,做一些措施
        String currentSkinPath = SkinPreUtils.getInstance(context).getSkinPath();
        File file = new File(currentSkinPath);
        if (!file.exists()) {
            //如果文件不存在,我们就清空皮肤
            SkinPreUtils.getInstance(mContext).clearSkinPath();
            return;
        }
        //获取包名 ,以防止被删
        String mPackageName = context.getPackageManager().getPackageArchiveInfo(currentSkinPath, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(mPackageName)) {
            //如果获取不到包名,同样清空
            SkinPreUtils.getInstance(mContext).clearSkinPath();
            return;
        }
        //做一些初始化的工作
        mSkinResource = new SkinResource(mContext, currentSkinPath);
        //后续做一下签名验证

    }

    public int loadSkin(String skinPath) {
        if (skinPath == null) {
            return SkinConfig.SKIN_NOTHING;
        }
        File file = new File(skinPath);
        if (!file.exists()) {
            //如果文件不存在,我们就清空皮肤
            SkinPreUtils.getInstance(mContext).clearSkinPath();
            return SkinConfig.SKIN_FILE_NO_EXSIST;
        }
        //获取包名 ,以防止被删
        String mPackageName = mContext.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(mPackageName)) {
            //如果获取不到包名,同样清空
            SkinPreUtils.getInstance(mContext).clearSkinPath();
            return SkinConfig.SKIN_FILE_ERROR;
        }
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (skinPath.equals(currentSkinPath)) {
            return SkinConfig.SKIN_NOTHING;
        }
        //初始化资源管理
        mSkinResource = new SkinResource(mContext, skinPath);
        //改变皮肤
        changeSkin();
        //保存皮肤的路径
        saveSkinStatus(skinPath);
        return 0;
    }

    /**
     * 实现换肤的方法
     */
    private void changeSkin() {
        Set<Activity> keys = mSkinViews.keySet();
        for (Activity key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
        }
    }

    /**
     * 保存已经切换过来的皮肤
     *
     * @param skinPath
     */
    private void saveSkinStatus(String skinPath) {
        SkinPreUtils.getInstance(mContext).saveSkinPath(skinPath);
    }

    /**
     * 获取SkinView 通过activity
     *
     * @param activity
     * @return
     */
    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);


    }

    public void register(Activity activity, List<SkinView> skinViews) {
        mSkinViews.put(activity, skinViews);

    }

    public SkinResource getSkinResource() {
        return mSkinResource;
    }

    /**
     * 恢复默认皮肤
     *
     * @return
     */
    public int restoreDefault() {
        //判断当前有没有皮肤
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (TextUtils.isEmpty(currentSkinPath)) {
            return SkinConfig.SKIN_NOTHING;
        }
        //当前手机运行的app的路径
        String skinPath = mContext.getPackageResourcePath();
        //初始化资源管理
        mSkinResource = new SkinResource(mContext, skinPath);
        changeSkin();
        //把皮肤信息清空
        SkinPreUtils.getInstance(mContext).clearSkinPath();
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 检查要不要换肤
     *
     * @param skinView
     */
    public void checkChangeSkin(SkinView skinView) {
        //如果当前有皮肤,也就是保存的皮肤的路径,就换一下
        String currentsSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (!TextUtils.isEmpty(currentsSkinPath)) {
            skinView.skin();
        }
    }
}
