package com.example.larry_sea.norember.utill.commonutils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Larry-sea on 2016/10/27.
 * <p>
 * 指纹识别工具类
 */
@TargetApi(23)
public class FingerPrintUtil {

    FingerprintManager manager;
    KeyguardManager mKeyManager;
    private final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0;
    private final static String TAG = "finger_log";
    Context context;
    inforAuthResult mInforInterface; //通知接口
    Activity mActivity;   //activity

    @TargetApi(23)
    private boolean isFinger() {

        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log(TAG, "有指纹权限");
        //判断硬件是否支持指纹识别
        if (!manager.isHardwareDetected()) {
            Toast.makeText(context, "没有指纹识别模块", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log(TAG, "有指纹模块");
        //判断 是否开启锁屏密码

        if (!mKeyManager.isKeyguardSecure()) {
            Toast.makeText(context, "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log(TAG, "已开启锁屏密码");
        //判断是否有指纹录入
        if (!manager.hasEnrolledFingerprints()) {
            Toast.makeText(context, "没有录入指纹", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log(TAG, "已录入指纹");

        return true;
    }

    private void Log(String tag, String msg) {
        Log.d(tag, msg);
    }


    CancellationSignal mCancellationSignal = new CancellationSignal();
    //回调方法
    FingerprintManager.AuthenticationCallback mSelfCancelled = new FingerprintManager.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            //但多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
            mInforInterface.inforActivity(false, true);
            Toast.makeText(context, errString, Toast.LENGTH_SHORT).show();
            showAuthenticationScreen();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            Toast.makeText(context, helpString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            if (mInforInterface != null) {
                mInforInterface.inforActivity(true, null);
                mCancellationSignal.cancel();
                Toast.makeText(context, "指纹识别成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAuthenticationFailed() {
            Toast.makeText(context, "指纹识别失败", Toast.LENGTH_SHORT).show();
        }
    };


    private void startListening(FingerprintManager.CryptoObject cryptoObject) {
        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            return;
        }
        manager.authenticate(cryptoObject, mCancellationSignal, 0, mSelfCancelled, null);


    }

    /**
     * 锁屏密码
     * <p>
     * 当输入错误次数过多的时候会调用这个函数并且启动锁屏
     */
    private void showAuthenticationScreen() {

        Intent intent = mKeyManager.createConfirmDeviceCredentialIntent("finger", "测试指纹识别");
        if (intent != null) {
//            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }
    }

    /*
    * 开始校验
    *
    * */
    public void startAuth(Activity activity) {
        context = activity;
        manager = (FingerprintManager) activity.getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyManager = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
        if (isFinger()) {
            Log(TAG, "keyi");
            startListening(null);
        }

    }


    /*
    *
    * 通知activity 检验结果
    *
    * */
    public interface inforAuthResult {
        /*
        *
        * @param result 代表验证结果是否为true还是false
        *
        * @param result 代表输入密码错误次数太多
        * */
        void inforActivity(Boolean result, @Nullable Boolean isTooMuchWrong);
    }


    /*
    *
    * 调用方在fingerActivity
    *
    * */
    public void setInforInterface(inforAuthResult inforInterface) {
        mInforInterface = inforInterface;
    }

}
