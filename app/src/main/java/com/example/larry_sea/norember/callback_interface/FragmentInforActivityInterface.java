package com.example.larry_sea.norember.callback_interface;

import android.os.Message;

/**
 * Created by Larry-sea on 9/25/2016.
 * <p>
 * fragment 通知 activity 的回调接口
 * <p>
 * 可以在fragment 与activity通信时使用
 */


public interface FragmentInforActivityInterface {

    void receiveMessage(Message message);

}
