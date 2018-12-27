package xst.app.com.essayjoke.observer;

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
    public static ObserverManager getInstance() {
        if (observerManager == null) {
            synchronized (ObserverManager.class) {
                if (observerManager == null) {
                    observerManager = new ObserverManager();
                }
            }
        }
        return observerManager;
    }

    /**
     * 加入监听队列
     *
     * @param observerListener
     */
    @Override
    public void add(ObserverListener observerListener) {
        list.add(observerListener);

    }

    /**
     * 通知观察者数据刷新
     *
     * @param content
     */
    @Override
    public void notifyObserver(String content) {
        for (ObserverListener observerListener : list) {
            observerListener.observerUpData(content);
        }
    }

    /**
     * 监听队列中移除
     *
     * @param observerListener
     */
    @Override
    public void remove(ObserverListener observerListener) {
        if (list.contains(observerListener)) {
            list.remove(observerListener);
        }
    }
}
