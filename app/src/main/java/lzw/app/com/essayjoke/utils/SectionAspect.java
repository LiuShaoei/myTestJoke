package lzw.app.com.essayjoke.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by LiuZhaowei on 2019/1/28 0028.
 */
@Aspect
public class SectionAspect {
    /**
     * 找到处理切点
     */
    @Pointcut("execution(@lzw.app.com.essayjoke.utils.CheckNet * *(..))")
    public void checkNetBehavior() {

    }

    /**
     * 处理切面
     */
    @Around("checkNetBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        //做埋点,
        //1.获取check注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet != null) {
            //2.判断有没有网络
            Object object = joinPoint.getThis();
            Context context = getContent(object);
            if (context != null) {
                if (!networkAvailable(context)) {
                    //没网,不要向下执行
                    Toast.makeText(context, "请检查您的网路", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 通过对象获取上下文
     *
     * @param object
     * @return
     */
    private Context getContent(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof View) {
            return ((View) object).getContext();
        }
        return null;
    }

    /**
     * 检查是否有网络连接
     *
     * @param context
     * @return
     */
    private boolean networkAvailable(Context context) {
        //连接到管理对象
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activityNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activityNetworkInfo != null && activityNetworkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
