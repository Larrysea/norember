package com.example.larry_sea.norember.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.example.larry_sea.norember.utill.StringUtil;

import java.util.List;

/**
 * Created by Larry-sea on 9/17/2016.
 * <p>
 * 短信接受者
 * <p>
 * 调用方在autologinservices中
 */

public class SmsRecevierBroadCast extends BroadcastReceiver {

    Bundle mbundle;
    List<String> identifyCode; //验证码
    inforAutoLoginServices minforAutoLoginServices;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.telephony.SMS_RECEIVED")) {

            Object[] pduses = (Object[]) (intent.getExtras()).get("pdus");          //获取接受的短信
            for (Object pdus : pduses) {
                byte[] pdusmessage = (byte[]) pdus;
                SmsMessage sms = SmsMessage.createFromPdu(pdusmessage);
                String mobile = sms.getOriginatingAddress();//发送短信的手机号码
                String content = sms.getMessageBody(); //短信内容
                identifyCode = StringUtil.getNumberFromString(content, 4, 10);
                //通知autologinservices进行填充验证码 取的信息链的第一个参数
                minforAutoLoginServices.inforAutoLogin(identifyCode.get(0));
            }
            mbundle = intent.getExtras();
        }
    }

    /*
    * 接口实现方在autologin services中
    *
    * */

    public void setInforAutoLoginServices(inforAutoLoginServices inforAutoLoginServices) {
        minforAutoLoginServices = inforAutoLoginServices;

    }

    public interface inforAutoLoginServices {
        void inforAutoLogin(String verificationNumber);
    }


}
