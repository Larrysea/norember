package com.example.larry_sea.norember.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.utill.commonutils.CommonUtil;
import com.example.larry_sea.norember.utill.commonutils.DeviceUtil;
import com.example.larry_sea.norember.utill.commonutils.DialogUtil;
import com.example.larry_sea.norember.utill.commonutils.FingerPrintUtil;
import com.example.larry_sea.norember.utill.commonutils.SharedPreferenceUtil;
import com.example.larry_sea.norember.utill.internet_util.InternetUtil;
import com.example.larry_sea.norember.view.activity.OpenAutoLoginSetting;
import com.example.larry_sea.norember.view.activity.ResetMainPasswordActivity;
import com.example.larry_sea.norember.view.activity.SyncActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Larry-sea on 10/4/2016.
 * <p>
 * 设置fragment
 */


public class SettingFragment extends Fragment implements FingerPrintUtil.inforAuthResult, InternetUtil.checkUpdateTaskImpl {


    FragmentInforActivityInterface fragmentInforActivityInterface;
    @BindView(R.id.id_layout_setting_auto_login_item_switch)
    SwitchCompat idLayoutSettingAutoLoginItemSwitch;
    @BindView(R.id.id_layout_setting_open_finger_print_item)
    RelativeLayout idLayoutSettingOpenFingerPrintItem;
    @BindView(R.id.id_layout_setting_open_fingerprint_item_switch)
    SwitchCompat idLayoutSettingOpenFingerprintItemSwitch;
    @BindView(R.id.id_layout_setting_open_finger_print_tv)
    TextView idLayoutSettingOpenFingerPrintTv;
    public final static String isFingerOpened = "IS_FINGER_LOCKED_OPEND"; //finger解锁是否开启
    public final static String sharedPreferenceFileName = "AppSetting";
    public final static String isAutoLoginOpened = "IS_AUTO_LOGIN_OPENED";  //自动登录服务是否打开
    String fileUrl;             //最新版本文件下载路径



    @OnClick(R.id.id_layout_setting_reset_password_item)
    void resetPasswordonClick(View view) {
        startActivity(new Intent(getActivity(), ResetMainPasswordActivity.class));
    }

    @OnClick(R.id.id_layout_setting_item_sync)
    void syncOnClick(View view) {
        startActivity(new Intent(getActivity(), SyncActivity.class));

    }

    @OnClick(R.id.id_layout_setting_contact_us_item)
    void contactUs(View view) {
        DeviceUtil.sendEmail(getActivity(), getResources().getString(R.string.app_mail_contact_address), getResources().getString(R.string.norember_user), "");
    }

    @OnClick(R.id.id_layout_setting_comment_item)
    void comment(View view) {
        ApplicationUtil.startCommentActivity(getActivity());
    }


    @OnClick(R.id.id_layout_setting_version_item)
    void checkVersion(View view) {
        InternetUtil internetUtil = new InternetUtil();
        internetUtil.setCheckUpdateInterface(this);
        try {
            internetUtil.checkUpdate(getActivity(), ApplicationUtil.getVersionName(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.id_layout_setting_bind_device_item)
    void bindDevice(View view) {
        Message message = new Message();
        message.obj = 1;
        if (fragmentInforActivityInterface != null)
            fragmentInforActivityInterface.receiveMessage(message);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_setting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    /*
    * 打开自动填充服务
    *
    * */
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT < 23) {
            idLayoutSettingOpenFingerPrintItem.setEnabled(false);
            idLayoutSettingOpenFingerprintItemSwitch.setEnabled(false);
            idLayoutSettingOpenFingerPrintTv.setEnabled(false);
        }
    }


    /*
    *
    * 绑定view与监听器
    *
    * */
    private void bindListener() {
        idLayoutSettingAutoLoginItemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferenceUtil.putSharedValue(getActivity(), sharedPreferenceFileName, isAutoLoginOpened, true);
                    startActivity(new Intent(getActivity(), OpenAutoLoginSetting.class));
                } else {
                    SharedPreferenceUtil.putSharedValue(getActivity(), sharedPreferenceFileName, isAutoLoginOpened, false);
                }
            }
        });
        idLayoutSettingOpenFingerprintItemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FingerPrintUtil fingerPrintUtil = new FingerPrintUtil();
                    fingerPrintUtil.setInforInterface((FingerPrintUtil.inforAuthResult) SettingFragment.this);
                    fingerPrintUtil.startAuth(getActivity());    //开始检验指纹
                }

            }
        });
    }


    /*
    * 设定接口
    * */
    public void setFragementInforActivityIf(FragmentInforActivityInterface fragementInforActivityIf) {
        this.fragmentInforActivityInterface = fragementInforActivityIf;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onResume() {
        super.onResume();
        initView();
    }


    /*
    * 初始view
    * */
    public void initView() {
        idLayoutSettingAutoLoginItemSwitch.setChecked(SharedPreferenceUtil.
                getBooleanSharedValue(getActivity(),
                        sharedPreferenceFileName, isAutoLoginOpened));
        idLayoutSettingOpenFingerprintItemSwitch.setChecked(SharedPreferenceUtil.
                getBooleanSharedValue(getActivity(), sharedPreferenceFileName, isFingerOpened));
        bindListener();
    }


    @Override
    public void inforActivity(Boolean result, Boolean isToomuchWrong) {
        SharedPreferenceUtil.putSharedValue(getActivity(), sharedPreferenceFileName, isFingerOpened, result);
    }


    /*
    *
    * 开始下载任务
    *
    * */
    @Override
    public void getTheResult(String fileUrl) {
        this.fileUrl = fileUrl;
        DialogUtil.getDialog(getActivity(), R.string.new_version, R.string.new_version_solve_some_bug, true, true, positiveOnClickListener, cancelClickListener);
    }

    DialogInterface.OnClickListener positiveOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (fileUrl != null) {
                dialog.dismiss();
                CommonUtil.startDownLoadingTask(getActivity(), fileUrl);
            }
        }
    };

    DialogInterface.OnClickListener cancelClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };
}
