package com.example.larry_sea.norember.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.manager.PasswordManager;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry-sea on 10/11/2016.
 * <p>
 * 改变主密码的activity
 */

public class ResetMainPasswordActivity extends AppCompatActivity {


    String firstPassword;          //第一次密码
    String secondPassword;         //第二次密码
    int progress = 1;              //修改进度    1 2 3  4    1代表 输入主密码  2代表输入新的密码  3代表再次输入新的密码  4代表设定成功而
    @BindView(R.id.id_reset_main_password_tip)
    TextView idResetMainPasswordTip;
    @BindView(R.id.id_reset_main_password_et)
    EditText idResetMainPasswordEt;
    @BindView(R.id.id_reset_main_password_next_btn)
    Button idResetMainPasswordNextBtn;
    @BindView(R.id.id_activity_reset_main_password_toolbar)
    Toolbar idActivityResetMainPasswordToolbar;
    boolean isLocked = false;                         //是否被锁屏

    @OnClick(R.id.id_reset_main_password_next_btn)
    void onClick(View view) {

        switch (progress) {
            case 1:
                if (PasswordManager.isPasswordCorrect(this, idResetMainPasswordEt.getText().toString())) {
                    progress++;
                    setUi(progress);
                }
                break;
            case 2:
                if (!idResetMainPasswordEt.getText().toString().isEmpty()) {
                    firstPassword = idResetMainPasswordEt.getText().toString();
                    progress++;
                    setUi(progress);
                } else {
                    Toast.makeText(this, R.string.password_cant_empty, Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if (!idResetMainPasswordEt.getText().toString().isEmpty()) {
                    if (firstPassword.equals(idResetMainPasswordEt.getText().toString())) {
                        progress++;
                        setUi(progress);
                    } else {
                        Toast.makeText(this, R.string.twice_password_not_same, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.password_cant_empty, Toast.LENGTH_SHORT).show();
                }
                break;


        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_main_password);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /*
            * 初始化view
            *
            * */
    public void initView() {
        idActivityResetMainPasswordToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        idActivityResetMainPasswordToolbar.setTitle(R.string.change_password);
        idActivityResetMainPasswordToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(idActivityResetMainPasswordToolbar);
        idResetMainPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    idResetMainPasswordNextBtn.setEnabled(false);
                } else {
                    idResetMainPasswordNextBtn.setEnabled(true);
                }
                ;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /*
    * 改变tip的内容和editetext的hint
    *
    * */
    public void changeTipAndEditeTextHint(int tipStringId, int editTextHintId) {
        idResetMainPasswordTip.setText(tipStringId);
        idResetMainPasswordEt.setHint(editTextHintId);


    }

    /*
    * 设定界面的提示语
    *
    * */
    public void setUi(int progress) {

        switch (progress) {
            case 2:
                changeTipAndEditeTextHint(R.string.chang_password_tip, R.string.please_input_new_password);
                break;
            case 3:
                changeTipAndEditeTextHint(R.string.chang_password_tip, R.string.please_input_again_password);
                break;
            case 4:
                progress = 1;
                changeTipAndEditeTextHint(R.string.change_password_succeed, R.string.change_password_succeed);
                idResetMainPasswordNextBtn.setVisibility(View.GONE);
                idResetMainPasswordEt.setVisibility(View.GONE);
                break;

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(ResetMainPasswordActivity.this, LockScreenActivity.class), 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            isLocked = false;
        } else if (resultCode == -1) {
            isLocked = true;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (ApplicationUtil.isApplicationInBackground(ResetMainPasswordActivity.this)) {
            isLocked = true;
        }
    }

}
