package lzw.app.com.framelibrary.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiuZhaowei on 2018/12/8 0008.
 * 自己的一套实现
 */
public class HttpUtils {
    //默认的网络引擎
    private static IHttpEngine mHttpEngine = new DefaultOkHttpEngine();

    private String mUrl;
    private int mType = GET_TYPE;
    private static final int POST_TYPE = 0X0011;
    private static final int GET_TYPE = 0X0011;
    private Map<String, Object> mParams;
    private Context mContext;

    private HttpUtils(Context context) {
        mContext = context;
        mParams = new HashMap<>();//
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) {
        this.mUrl = url;
        return this;
    }

    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }

    //添加参数

    public HttpUtils addParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public HttpUtils addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    //可以再Application初始化引擎
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    //添加回调执行
    public HttpUtils execute(EngineCallBack callBack) {
        if (callBack == null) {
            callBack = EngineCallBack.DEFAULT_CALL_BACK;
        }
        if (mType == POST_TYPE) {
            post(mUrl, mParams, callBack);
        }
        if (mType == GET_TYPE) {
            get(mUrl, mParams, callBack);
        }

        return this;
    }

    public void execeute() {
        execute(null);
    }

    /**
     * 每次可以自带引擎
     *
     * @param httpEngine
     */
    public void exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }


    private void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.get(mContext,url, params, callBack);
    }

    private void post(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.get(mContext,url, params, callBack);
    }
}
