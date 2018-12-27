package xst.app.com.essayjoke.observer;

/**
 * Created by LiuZhaowei on 2018/12/26 0026.
 * 被观察者接口
 */
public interface SubjectListener {
    void add(ObserverListener observerListener);
    void notifyObserver(String content);
    void remove(ObserverListener observerListener);
}
