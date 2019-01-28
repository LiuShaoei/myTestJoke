package lzw.app.com.baselibrary.tag;

/**
 * Created by LiuZhaowei on 2018/12/26 0026.
 * 被观察者接口
 */
public interface SubjectListener {
    void registerObserver(ObserverListener observerListener);
    void notifyObserver(int code,int position);
    void unregisterObserver(ObserverListener observerListener);
    void unregisterAll();
}
