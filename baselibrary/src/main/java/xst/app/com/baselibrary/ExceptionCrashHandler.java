package xst.app.com.baselibrary;

import android.content.Context;
import android.util.Log;

/**
 * Created by LiuZhaowei on 2018/12/6 0006.
 * 单例的设计模式的异常捕捉
 * 因为异常有时不可重现,把崩溃的信息保存到内存卡中,
 * 等上线后将内存卡中的信息上传到服务器
 */
public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {
    private static ExceptionCrashHandler mInstance;
    private static final String TAG = "异常";
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    public static ExceptionCrashHandler getInstance() {
        if (mInstance == null) {
            //用于解决多并发的问题
            synchronized (ExceptionCrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new ExceptionCrashHandler();
                }
            }
        }
        return mInstance;
    }

    //用来获取应用的信息
    private Context mContext;

    public void init(Context context) {
        this.mContext = context;
        //设置全局的异常本类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mDefaultExceptionHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //全局异常
        Log.e(TAG, "报错了");
        //写入本地文件 当前版本等

        //1.崩溃的详细信息,后期完善

        //2.应用信息包名,版本号等

        //3.手机信息,当前手机

        //4.上传的问题,保存当前文件,等应用再次启动再上传,上传文件不在这里处理

        //让系统的默认处理一下
        mDefaultExceptionHandler.uncaughtException(t, e);

    }
}
