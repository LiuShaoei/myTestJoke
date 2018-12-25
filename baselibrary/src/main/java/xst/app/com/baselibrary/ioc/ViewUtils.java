package xst.app.com.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by LiuZhaowei on 2018/12/5 0005.
 */
public class ViewUtils {
    //目前要用的在activity使用
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    //在view里使用
    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    //兼容上面三个方法  object ->反射需要执行的类
    private static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);

    }

    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        //1.获取类里面的所有属性
        Class<?> clazz = object.getClass();
        //获取所有属性,包括私有和共有
        Field[] fields = clazz.getDeclaredFields();
        //2.获取viewById里面的value
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //获取注释里面的id值 -->R.di.test
                int viewId = viewById.value();
                //3.findViewById 找到View
                View view = finder.findViewById(viewId);
                if (view != null) {
                    //能够注入所有的修饰符 private public
                    field.setAccessible(true);
                    // 4.动态的注入找到view
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    /**
     * 事件注入
     *
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        //1.获取类里面所有的方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        //2.获取Onclick 里面的value值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    View view = finder.findViewById(viewId);
                    //扩展功能,检查网络
                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;
                    if (view != null) {
                        view.setOnClickListener(new DeclaredOnClickListener(method, object, isCheckNet));
                    }
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Object mObject;
        private Method mMethod;
        private boolean mIsCheckNet;

        public DeclaredOnClickListener(Method method, Object object, boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {
            //先判断需不需要检查网络
            if (mIsCheckNet) {
                //需要
                if (!networkAvailable(v.getContext())) {
                    //打印日志
                    Toast.makeText(v.getContext(), "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            //点击调用该方法
            try {
                //能够注入所有的修饰符 private public
                mMethod.setAccessible(true);
                mMethod.invoke(mObject, v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject, null,null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     *检查是否有网络连接
     * @param context
     * @return
     */
    private static boolean networkAvailable(Context context) {
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
