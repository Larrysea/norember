package com.example.larry_sea.norember.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.fragment.PasswordFragment;

/**
 * Created by Larry-sea on 9/25/2016.
 * <p>
 * 显示password的fragment
 */

public class PasswordActivity extends AppCompatActivity {
    PasswordFragment mbasePasswordFragment;
    boolean isLocked = false;                         //是否被锁屏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_fragment_activity);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        initView();
    }

    public void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mbasePasswordFragment = new PasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", getIntent().getStringExtra("key"));
        mbasePasswordFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_fragment_container, mbasePasswordFragment, "idcardFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(PasswordActivity.this, LockScreenActivity.class), 1);
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
        if (ApplicationUtil.isApplicationInBackground(PasswordActivity.this)) {
            isLocked = true;
        }
    }

}
