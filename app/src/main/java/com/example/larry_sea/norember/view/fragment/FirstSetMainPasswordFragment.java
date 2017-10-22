package com.example.larry_sea.norember.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.manager.PasswordManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry-sea on 10/14/2016.
 */

public class FirstSetMainPasswordFragment extends Fragment {


    FragmentInforActivityInterface minterface;
    @BindView(R.id.id_firstset_main_password_next_btn)
    Button idFirstsetMainPasswordNextBtn;
    @BindView(R.id.id_firstset_main_password_et)
    EditText idFirstsetMainPasswordEt;

    @OnClick(R.id.id_firstset_main_password_next_btn)
    void onClick(View view) {
        if (!idFirstsetMainPasswordEt.getText().toString().isEmpty()) {
            if (idFirstsetMainPasswordEt.getText().toString().length() <= 6) {
                Toast.makeText(getActivity(), R.string.password_must_max_six, Toast.LENGTH_SHORT).show();
            } else {
                if (minterface != null) {
                    PasswordManager.setFirstPassword(idFirstsetMainPasswordEt.getText().toString());
                    minterface.receiveMessage(null);
                }
            }

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firset_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public void setInterface(FragmentInforActivityInterface fragmentInforActivityInterface) {
        this.minterface = fragmentInforActivityInterface;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        idFirstsetMainPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    idFirstsetMainPasswordNextBtn.setEnabled(true);
                } else {
                    idFirstsetMainPasswordNextBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
