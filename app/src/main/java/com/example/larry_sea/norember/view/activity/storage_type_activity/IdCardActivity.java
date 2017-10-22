package com.example.larry_sea.norember.view.activity.storage_type_activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.activity.LockScreenActivity;
import com.example.larry_sea.norember.view.fragment.storage_type_fragment.IdcardFragment;

/**
 * Created by Larry-sea on 9/27/2016.
 * <p>
 * 身份证activity
 */

public class IdCardActivity extends AppCompatActivity {

    IdcardFragment idcardFragment;
    Dialog mDialog;
    boolean isLocked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_fragment_activity);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        initView();
    }

    public void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        idcardFragment = new IdcardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", getIntent().getStringExtra("key"));
        idcardFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_fragment_container, idcardFragment, "idcardFragment");
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && idcardFragment.isEditAble()) {
            idcardFragment.showWetherSaveDialog();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(IdCardActivity.this, LockScreenActivity.class), 1);
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
        if (ApplicationUtil.isApplicationInBackground(IdCardActivity.this)) {
            isLocked = true;
        }
    }

}
