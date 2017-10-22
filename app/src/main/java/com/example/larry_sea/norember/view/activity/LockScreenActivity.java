package com.example.larry_sea.norember.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.larry_sea.norember.MainActivity;
import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.broadcast.ScreenLockBroadCastReceiver;
import com.example.larry_sea.norember.manager.PasswordManager;
import com.example.larry_sea.norember.utill.commonutils.FingerPrintUtil;
import com.example.larry_sea.norember.utill.commonutils.SharedPreferenceUtil;
import com.example.larry_sea.norember.view.customer.floatwindow.MyWindowManager;
import com.example.larry_sea.norember.view.fragment.SettingFragment;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 10/12/2016.
 * <p>
 * 锁屏activity
 */

public class LockScreenActivity extends AppCompatActivity implements FingerPrintUtil.inforAuthResult {
    @BindView(R.id.id_fragment_lock_et)
    EditText idFragmentLockEt;
    @BindView(R.id.id_fragment_lock_finger_iv)
    ImageView idFragmentLockFingerIv;
    boolean isIntentEmpty = true;           //intent是否为空
    boolean isCorrect = true;

    final static String TAG = LockScreenActivity.class.toString();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoginActivity();
        setContentView(R.layout.fragment_lock);
        ButterKnife.bind(this);
        initView();
        if (getIntent().getStringExtra("call_source") != null) {
            isIntentEmpty = false;
        }
        if (getParent() != null) {
            Log.e(TAG, "LockScreenActivity is 产生了" + getParent().toString());

        }
        Log.e(TAG, "LockScreenActivity is 产生了");

    }


    /*
    *
    * 弹出键盘
    *
    * */
    public void popupKeyBoard() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) idFragmentLockEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(idFragmentLockEt, 0);
            }
        }, 500);
    }


    /*
    * 初始化view
    *
    * */
    public void initView() {
        bindEditTextListener();
        //设定是否显示imageView
        if (SharedPreferenceUtil.getBooleanSharedValue(LockScreenActivity.this,
                SettingFragment.sharedPreferenceFileName, SettingFragment.isFingerOpened)) {
            idFragmentLockFingerIv.setVisibility(View.VISIBLE);
            //开始指纹识别
            FingerPrintUtil fingerPrintutil = new FingerPrintUtil();
            fingerPrintutil.setInforInterface(this);
            fingerPrintutil.startAuth(LockScreenActivity.this);
        } else {
            popupKeyBoard();
        }


    }


    /*
    * 绑定editeTextListener
    *
    * */
    private void bindEditTextListener() {
        idFragmentLockEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isIntentEmpty) {    //当从程序主界面调用时
                    if (checkIsCorrect(s)) {
                        setFeedResult();
                    } else {
                        if (isCorrect)
                            setResult(-1);
                    }
                } else {  //当小窗口调用这个时使用此方法
                    if (checkIsCorrect(s)) {
                        LockScreenActivity.this.finish();
                        Handler handler = new Handler();
                        handler.postAtTime(new Runnable() {
                            @Override
                            public void run() {
                                MyWindowManager.createBigWindow(LockScreenActivity.this);
                            }
                        }, 500);
                    } else {
                        MyWindowManager.removeSmallWindow(LockScreenActivity.this);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /*
    *
    * 判断密码是否正确
    *
    *
    *
    * */
    private boolean checkIsCorrect(CharSequence s) {
        if (s.length() == PasswordManager.getPasswordLength(LockScreenActivity.this)) {
            if (PasswordManager.isPasswordCorrect(LockScreenActivity.this, idFragmentLockEt.getText().toString())) {
                return true;
            } else {
                isCorrect = false;
                return false;
            }
        }
        return false;
    }

    private void setFeedResult() {
        ScreenLockBroadCastReceiver.delock();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        MainActivity.delock();
        LockScreenActivity.this.finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MainActivity.finishThis();
            LockScreenActivity.this.finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "销毁");
        super.onDestroy();
    }

    @Override
    public void inforActivity(Boolean result, Boolean isTooMuchWrong) {
        if (result) {
            MainActivity.delock();
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        } else {
            if (isTooMuchWrong != null && isTooMuchWrong) {
                idFragmentLockFingerIv.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 设置登录 activity的初始activity是选择初始设定activity还是锁定activity
     */
    public void initLoginActivity() {

        if (PasswordManager.getPassword(this) == null) {
            this.finish();
            startActivity(new Intent(this, WelcomeActivity.class));
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            this.finish();
            startActivity(new Intent(LockScreenActivity.this, MainActivity.class));
        } else if (resultCode == -1) {
            Toast.makeText(LockScreenActivity.this, R.string.password_not_correct, Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }


}
