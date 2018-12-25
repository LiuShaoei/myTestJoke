package xst.app.com.essayjoke.other;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import xst.app.com.essayjoke.UserAidl;

/**
 * Created by LiuZhaowei on 2018/12/10 0010.
 */
public class MessageService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //绑定
        return mBinder;
    }


    private final UserAidl.Stub mBinder = new UserAidl.Stub() {
        @Override
        public String getUserName() throws RemoteException {
            return "asdfasdfs@qq.com";
        }

        @Override
        public String getUserPwd() throws RemoteException {
            return "size55555555";
        }
    };
}
