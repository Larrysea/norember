package com.example.larry_sea.norember.view.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.view.fragment.FirstSetMainPasswordFragment;
import com.example.larry_sea.norember.view.fragment.SurePasswordFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 10/14/2016.
 * <p>
 * 欢迎activity
 */

public class WelcomeActivity extends AppCompatActivity implements FragmentInforActivityInterface {


    FirstSetMainPasswordFragment firstPasswordFragment;
    @BindView(R.id.id_welcome_container)
    FrameLayout idWelcomeContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        firstPasswordFragment = new FirstSetMainPasswordFragment();
        firstPasswordFragment.setInterface(this);

        getSupportFragmentManager().beginTransaction().add(R.id.id_welcome_container, firstPasswordFragment, "first_set_password_fragment").commit();

    }


    @Override
    public void receiveMessage(Message message) {
        SurePasswordFragment surePasswordFragment = new SurePasswordFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.id_welcome_container, surePasswordFragment, "surepasswordFragment").commit();
    }
}
