package xst.app.com.framelibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by LiuZhaowei on 2018/12/8 0008.
 * 网络引擎的规范
 */
public interface IHttpEngine {

    //get请求
    void get(Context context,String url, Map<String, Object> params, EngineCallBack callBack);

    //post请求
    void post(Context context,String url, Map<String, Object> params, EngineCallBack callBack);
    //下载文件

    //上传文件

    //https 添加证书
}
