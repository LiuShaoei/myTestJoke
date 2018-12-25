package xst.app.com.essayjoke.other;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;
import xst.app.com.baselibrary.ExceptionCrashHandler;
import xst.app.com.framelibrary.http.DefaultOkHttpEngine;
import xst.app.com.framelibrary.http.HttpUtils;
import xst.app.com.framelibrary.skin.SkinManager;

/**
 * Created by LiuZhaowei on 2018/12/6 0006.
 */
public class BaseApplication extends Application {

    public static PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpUtils.init(new DefaultOkHttpEngine());
        //设置全局的异常捕捉类
        ExceptionCrashHandler.getInstance().init(this);

        SkinManager.getInstance().init(this);

        //初始化阿里的热修复
        // mPatchManager = new PatchManager(this);
        //初始化版本,获取当前应用的版本
       /* try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            String version = info.versionName;
            mPatchManager.init(version);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //加载之前的包
        mPatchManager.loadPatch();*/
        // FixDexManager fixDexManager = new FixDexManager(this);
        // fixDexManager.loadFixDex();
    }

}
