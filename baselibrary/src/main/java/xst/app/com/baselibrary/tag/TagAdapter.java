package xst.app.com.baselibrary.tag;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiuZhaowei on 2018/12/25 0025.
 * 流式布局
 */
public abstract class TagAdapter{


    private final ObserverManager mObserverManager = new ObserverManager();

    //1.有多少个条目
    public abstract int getCount();

    //2.当前的位置
    public abstract View getView(int position, ViewGroup parent);
    //观察者模式及时通知更新

    //3.1 移除
    public void unregisterObserver(ObserverListener observerListener) {
        mObserverManager.unregisterObserver(observerListener);
    }

    //3.2 注册
    public void registerObserver(ObserverListener observerListener) {
        mObserverManager.registerObserver(observerListener);

    }

    public void notifyObserver(int code,int position) {
        mObserverManager.notifyObserver(code,position);

    }

    public void unregisterAll(){
        mObserverManager.unregisterAll();
    }
}
