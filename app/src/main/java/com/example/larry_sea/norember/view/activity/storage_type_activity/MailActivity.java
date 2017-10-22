package com.example.larry_sea.norember.view.activity.storage_type_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.activity.LockScreenActivity;
import com.example.larry_sea.norember.view.fragment.storage_type_fragment.MailinforFragment;

/**
 * Created by Larry-sea on 9/28/2016.
 * <p>
 * <p>
 * 邮箱activity
 */

public class MailActivity extends AppCompatActivity {
    MailinforFragment mailinforFragment;
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
        mailinforFragment = new MailinforFragment();
        Bundle bundle = new Bundle();
        if (getIntent().getStringExtra("key") != null)
            bundle.putString("key", getIntent().getStringExtra("key"));
        mailinforFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_fragment_container, mailinforFragment, "idcardFragment");
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mailinforFragment.isEditAble()) {
            mailinforFragment.showWetherSaveDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(MailActivity.this, LockScreenActivity.class), 1);
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
        if (ApplicationUtil.isApplicationInBackground(MailActivity.this)) {
            isLocked = true;
        }
    }


}
