package lzw.app.com.baselibrary.tag;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuZhaowei on 2018/12/26 0026.
 */
public class ObserverManager implements SubjectListener {

    private static ObserverManager observerManager;
    //观察者接口集合
    private List<ObserverListener> list = new ArrayList<>();

    /**
     * 单例
     */
//    public static ObserverManager getInstance() {
//        if (observerManager == null) {
//            synchronized (ObserverManager.class) {
//                if (observerManager == null) {
//                    observerManager = new ObserverManager();
//                }
//            }
//        }
//        return observerManager;
//    }

    /**
     * 加入监听队列
     *
     * @param observerListener
     */
    @Override
    public void registerObserver(ObserverListener observerListener) {
        if (observerListener == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized (list) {
            if (list.contains(observerListener)) {
                throw new IllegalStateException("Observer is already registered.");
            }

            list.add(observerListener);
        }
    }

    /**
     * 通知观察者数据刷新
     */
    @Override
    public void notifyObserver(int code,int position) {
        if (list == null || list.size() == 0) {
            Log.i("love","没有");
            return;
        }
        for (ObserverListener observerListener : list) {
            observerListener.observerUpData(code,position);
        }
    }

    /**
     * 监听队列中移除
     *
     * @param observerListener
     */
    @Override
    public void unregisterObserver(ObserverListener observerListener) {
        if (list == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized (list) {
            int index = list.indexOf(observerListener);
            if (index == -1) {
                throw new IllegalStateException("Observer  was not registered.");
            }
            list.remove(index);
        }
//        if (list.contains(observerListener)) {
//            list.remove(observerListener);
//        }
    }

    /**
     * 移除所有观察者
     */
    public void unregisterAll() {
        synchronized(list) {
            list.clear();
        }
    }}
