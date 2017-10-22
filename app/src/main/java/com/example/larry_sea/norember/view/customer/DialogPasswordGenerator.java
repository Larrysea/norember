package com.example.larry_sea.norember.view.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.service.AutoLoginService;
import com.example.larry_sea.norember.utill.CheckPswMeter;
import com.example.larry_sea.norember.utill.CopyUtil;
import com.example.larry_sea.norember.utill.PasswordUtil;
import com.rey.material.widget.ImageButton;
import com.rey.material.widget.Slider;
import com.rey.material.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Larry-sea on 2016/9/4.
 */
public class DialogPasswordGenerator extends DialogFragment {


    static final int UPDATE_FLAG = 0x1;
    String mpassword;                  //生成的密码项
    @BindView(R.id.id_password_copy_btn)
    ImageButton copyBtn;
    @BindView(R.id.id_password_et)
    EditText idPasswordEt;
    @BindView(R.id.id_length_slider)
    Slider length_slider;


    @BindView(R.id.id_password_strength_tv)
    TextView passwordStrengthTv;
    @BindView(R.id.id_letter_switch)
    Switch letter_switch;
    @BindView(R.id.id_number_switch)
    Switch number_switch;
    @BindView(R.id.id_symbol_switch)
    Switch symbol_switch;
    @BindView(R.id.id_length_tv)
    TextView idLengthTv;
    CheckPswMeter checkPswMeter;
    @BindView(R.id.id_password_cancel_btn)
    Button idPasswordCancelBtn;
    @BindView(R.id.id_password_sure_btn)
    Button idPasswordSureBtn;
    Message message;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_FLAG:
                    idPasswordEt.setText(msg.obj.toString());
                    passwordStrengthTv.setText("强度 ：" + checkPswMeter.getPasswordStrength(msg.obj.toString()));
                    mpassword = idPasswordEt.getText().toString();
                    idPasswordEt.setSelection(mpassword.length());
                    break;


            }
        }
    };

    @OnClick(R.id.id_password_copy_btn)
    void copyOnClick(View view) {
        if (mpassword != null)
            CopyUtil.setCopyData(getContext(), mpassword);
        Toast.makeText(getActivity(), R.string.copy_success, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.id_password_cancel_btn)
    void cancelOnClick(View view) {
        this.dismiss();
    }

    @OnClick(R.id.id_password_sure_btn)
    void sureOnClick(View view) {
        this.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_activity_password_generator, container, false);
        ButterKnife.bind(this, view);
        checkPswMeter = new CheckPswMeter();
        sendUpdateMessage(10);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        bindListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        length_slider = (Slider) view.findViewById(R.id.id_length_slider);
        length_slider.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                sendUpdateMessage(newValue);
            }
        });


    }

    public String generatePassword(int length, boolean containLetter, boolean containNumber, boolean containSymbol) {

        PasswordUtil passwordUtil = new PasswordUtil();
        return passwordUtil.generatePassword(length, true, true, containNumber, containSymbol, 3, false, false);

    }

    /*
    *
    * 绑定组件的监听器
    *
    *
    * */
    public void bindListener() {
        letter_switch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                sendUpdateMessage(length_slider.getValue());
            }
        });

        number_switch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                sendUpdateMessage(length_slider.getValue());

            }
        });
        symbol_switch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                sendUpdateMessage(length_slider.getValue());

            }
        });
    }

    public void sendUpdateMessage(int passwordLength) {

        message = new Message();
        message.what = 0x01;
        message.obj = generatePassword((int) passwordLength, letter_switch.isChecked(), number_switch.isChecked(), symbol_switch.isChecked());
        handler.sendMessage(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager windmanager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParms = getDialog().getWindow().getAttributes();
        layoutParms.width = (int) (windmanager.getDefaultDisplay().getWidth() * 0.8);
        getDialog().getWindow().setAttributes(layoutParms);
        Intent intent = new Intent(getContext(), AutoLoginService.class);
        getContext().startService(intent);
    }


    public interface passwordReturnListener {
        void returnPassword(String password);
    }
}
