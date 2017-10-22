package com.example.larry_sea.norember.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.larry_sea.norember.MainActivity;
import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.manager.PasswordManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry-sea on 10/14/2016.
 * <p>
 * 确认用户密码的fragment
 */


public class SurePasswordFragment extends Fragment {
    @BindView(R.id.id_sure_main_password_next_btn)
    Button idSureMainPasswordNextBtn;
    @BindView(R.id.id_sure_main_password_et)
    EditText idSureMainPasswordEt;

    @OnClick(R.id.id_sure_main_password_next_btn)
    void onClick(View view) {
        if (PasswordManager.getFirstPassword().equals(idSureMainPasswordEt.getText().toString())) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), MainActivity.class));
            PasswordManager.savePassword(getActivity(), idSureMainPasswordEt.getText().toString());
        } else {
            Toast.makeText(getActivity(), R.string.twice_password_not_same, Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sure_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindListener();
    }


    /*
    * 绑定监听器
    *
    * */
    private void bindListener() {
        idSureMainPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    idSureMainPasswordNextBtn.setEnabled(true);
                } else {
                    idSureMainPasswordNextBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                idSureMainPasswordEt.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, idSureMainPasswordEt.getLeft() + 5, idSureMainPasswordEt.getTop() + 5, 0));
                idSureMainPasswordEt.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, idSureMainPasswordEt.getLeft() + 5, idSureMainPasswordEt.getTop() + 5, 0));

            }
        }, 500);

    }
}
