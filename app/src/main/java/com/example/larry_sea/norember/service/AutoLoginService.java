package com.example.larry_sea.norember.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.InputType;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.broadcast.SmsRecevierBroadCast;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.customer.floatwindow.FloatWindowBigView;
import com.example.larry_sea.norember.view.customer.floatwindow.MyWindowManager;

import java.util.List;

/**
 * Created by Larry-sea on 2016/9/11.
 * <p/>
 * 实现自动登录的服务
 */
public class AutoLoginService extends AccessibilityService implements FloatWindowBigView.inforService, SmsRecevierBroadCast.inforAutoLoginServices {


    String TAG = "Auto login services";
    SmsRecevierBroadCast smsRecevierBroadCast;           //短信接受广播
    String accountName;                                  //用户名
    String password;                                     //密码
    boolean isFilled = false;                            //是否填充
    AccessibilityNodeInfo mAccessibilityNodeInfo;        //节点信息
    boolean hasFirstEditText = false;                    //是否包含第一个editText

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        if (accessibilityEvent != null) {
            int eventType = accessibilityEvent.getEventType();
            if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                findViewAndFillPassword();

            }
        }


    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "auto login service is interrupt");


    }


    @Override
    protected void onServiceConnected() {
        Log.e(TAG, "auto login service is started");
    }


    /*
    *
    * 找到登录界面的组件并且填充
    * */
    public void findViewAndFillPassword() {
        if (Build.VERSION.SDK_INT > 18) {
            if (getRootInActiveWindow() != null) {
                hasFirstEditText = false;
                recycleMax19(getRootInActiveWindow());
            }
        } else {
            if (getRootInActiveWindow() != null) {
                hasFirstEditText = false;
                recycleLess18(new AccessibilityNodeInfoCompat(getRootInActiveWindow()));
            }

        }
    }


    @TargetApi(18)
    public void recycleLess18(AccessibilityNodeInfoCompat info) {
        if (info != null) {
            if (info.getChildCount() == 0) {
                processViewType(info);
            } else {
                for (int i = 0; i < info.getChildCount(); i++) {
                    if (info.getChild(i) != null) {
                        recycleLess18(info.getChild(i));
                    }
                }
            }
        }
    }


    /*
    *
    * 遍历viewtree并且获取两个节点信息
    *
    * 包括account 节点信息
    * 包括password 包含密码节点信息
    *
    * */
    @TargetApi(19)
    public void recycleMax19(AccessibilityNodeInfo info) {
        if (info != null) {
            if (info.getChildCount() == 0) {
                //处理节点信息
                processViewType(info);
            } else {
                for (int i = 0; i < info.getChildCount(); i++) {
                    if (info.getChild(i) != null) {
                        recycleMax19(info.getChild(i));
                    }
                }
            }
        }

    }


    /*
    *
    * 通知服务进行填充
    *
    * 实现方在floatwindow big view
    *
    * */
    @Override
    public void inforServiceFillAccount(String account, String password) {
        this.accountName = account;
        this.password = password;
        //可以进行填充
        isFilled = true;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                findViewAndFillPassword();
            }
        }, 1000);
    }

    @Override
    public void inforSerViceFillPhoneNumber(String phone) {
        //fillPhoneNumberAndClickNext(phone);
        //通知处理填充电话号
    }


    /*
    * 处理不同view 类型并且做相关的初始化工作
    *
    * */
    @TargetApi(21)
    public void processViewType(AccessibilityNodeInfo info) {
          /* 普通账户型的edittext*/
        if (info == null)
            return;
        if (info.getClassName().equals("android.widget.EditText") && info.getInputType() == 65537) {
            hasFirstEditText = true;
            if (isFilled) {
                Bundle bundle = new Bundle();
                bundle.putCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, accountName);
                info.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
                info.refresh();
            }
        }
         /*密码类型的editext*/
        else if (info.getClassName().equals("android.widget.EditText") && (info.getInputType() == 129)) {
            if (isFilled) {
                Bundle bundle = new Bundle();
                bundle.putCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, password);
                info.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
                info.refresh();
                clickButton(getResources().getString(R.string.login));
                removeWindow();
                isFilled = false;
            } else if (hasFirstEditText) {
                System.out.println("打开窗口");
                addWindow();
            }
        }
        /*手机类型的editext*/
        else if ((info.getClassName().equals("android.widget.EditText") && info.getInputType() == 2)) {
            info = info;
            clickButton(getResources().getString(R.string.next_step));
        }
        /*下一项按钮类button*/
        else if (info.getClassName().equals("android.widget.Button") && info.getText() != null && info.getText().toString().equals(getResources().getString(R.string.next_step))) {
            info = info;
            if (info != null) {
                MyWindowManager.createSmallWindow(getApplicationContext(), (FloatWindowBigView.inforService) this);
            }
        }


    }






    /*
    * 自动注册手机账号
    *
    * */
    /*public void fillPhoneNumberAndClickNext(String phoneNumber,AccessibilityNodeInfo phoneNodeInfo,A) {
        Bundle phoneBundle = new Bundle();
        phoneBundle.putCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, phoneNumber);
        phoneNodeInfo.performAction(AccessibilityNodeInfoCompat.ACTION_SET_TEXT, phoneBundle);
        phoneNodeInfo.refresh();
        // 判断按钮是否可以点击可以点击则执行点击操作
        if (nextStepNodeInfo.isClickable()) {
            nextStepNodeInfo.performAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
        }
        //启动广播监听接受短信内容
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        smsRecevierBroadCast = new SmsRecevierBroadCast();
        smsRecevierBroadCast.setInforAutoLoginServices((SmsRecevierBroadCast.inforAutoLoginServices) this);
        this.registerReceiver(smsRecevierBroadCast, intentFilter);


    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (smsRecevierBroadCast != null) {
            unregisterReceiver(smsRecevierBroadCast);
        }
        Log.e(TAG, "login services is destory");
    }

    /*
    * 通知填充验证码
    *
    * */
    @Override
    public void inforAutoLogin(String verificationNumber) {


    }


    /*
    *
    * 模拟点击按钮
    * @param
    *
    *
    * */
    public void clickButton(String buttonText) {
        int buttonPosition = 0;  //按钮的位置
        List<AccessibilityNodeInfo> nodeInfos = getRootInActiveWindow().findAccessibilityNodeInfosByText(buttonText);
        for (int position = 0; position < nodeInfos.size(); position++) {
            if (nodeInfos.get(position).getClassName().toString().equals("android.widget.Button")) {
                buttonPosition = position;
                break;
            }
        }
        if (nodeInfos.size() - 1 >= buttonPosition && nodeInfos.get(buttonPosition).isClickable()) {
            nodeInfos.get(buttonPosition).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }

    }

    @TargetApi(18)
    public void processViewType(AccessibilityNodeInfoCompat info) {
          /* 普通账户型的editText*/
        if (info == null)
            return;
        if (info.getClassName().equals("android.widget.EditText") && info.getText() != null) {
            hasFirstEditText = true;
            if (isFilled) {
                Bundle bundle = new Bundle();
                bundle.putCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, accountName);
                info.performAction(AccessibilityNodeInfoCompat.ACTION_SET_TEXT, bundle);
                info.refresh();
            }
        }
         /*密码类型的editext*/
        else if (info.getClassName().equals("android.widget.EditText") && (info.getText() == null) && ApplicationUtil.isApplicationInBackground(getApplicationContext())) {
            if (isFilled) {
                Bundle bundle = new Bundle();
                bundle.putCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, password);
                info.performAction(AccessibilityNodeInfoCompat.ACTION_SET_TEXT, bundle);
                info.refresh();
                clickButton(getResources().getString(R.string.login));
                removeWindow();
                isFilled = false;
            } else if (hasFirstEditText) {
                addWindow();
            }
        }
        /*手机类型的editext*/
        else if ((info.getClassName().equals("android.widget.EditText") && info.getInputType() == 2)) {
            clickButton(getResources().getString(R.string.next_step));
        }
        /*下一项按钮类button*/
        else if (info.getClassName().equals("android.widget.Button") && info.getText() != null && info.getText().toString().equals(getResources().getString(R.string.next_step))) {
            {
                MyWindowManager.createSmallWindow(getApplicationContext(), (FloatWindowBigView.inforService) this);
            }
        }


    }


    public void addWindow() {
        MyWindowManager.createSmallWindow(AutoLoginService.this, this);

    }

    public void removeWindow() {
        MyWindowManager.removeSmallWindow(getApplicationContext());
    }


    public boolean isPassWordType(int inputType) {
        if (inputType == InputType.TYPE_NUMBER_VARIATION_PASSWORD || inputType == InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD || inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            return true;
        }
        return false;
    }


}
