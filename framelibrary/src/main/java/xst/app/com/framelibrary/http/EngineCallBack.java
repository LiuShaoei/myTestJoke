package xst.app.com.framelibrary.http;

/**
 * Created by LiuZhaowei on 2018/12/8 0008.
 */
public interface EngineCallBack{
    //错误
    public void onError(Exception e);

    //成功
    public void onSuccess(String result);

    //默认的
    public final EngineCallBack DEFAULT_CALL_BACK= new EngineCallBack() {
        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
