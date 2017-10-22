package com.example.larry_sea.norember.view.activity.list_storage_type_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.activity.LockScreenActivity;
import com.example.larry_sea.norember.view.activity.storage_type_activity.MailActivity;
import com.example.larry_sea.norember.view.fragment.list_storage_type_fragement.ListMailInforFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 9/28/2016.
 * <p>
 * <p>
 * 展示邮箱的list activity
 */

public class MailInforListActivity extends AppCompatActivity implements FragmentInforActivityInterface {


    ListMailInforFragment listLoginInforFragment;
    @BindView(R.id.id_activity_common_list_toolbar)
    Toolbar idActivityCommonListToolbar;
    boolean isLocked = false;                         //是否被锁屏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);
        ButterKnife.bind(this);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        initView();
    }

    public void initView() {
        idActivityCommonListToolbar.setTitle(R.string.mail);
        idActivityCommonListToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        idActivityCommonListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MailInforListActivity.this.finish();
            }
        });
        setSupportActionBar(idActivityCommonListToolbar);
        listLoginInforFragment = new ListMailInforFragment();
        listLoginInforFragment.setInforActivityInterface(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_activity_common_list_container, listLoginInforFragment, "MailInforListFragment");
        fragmentTransaction.commit();

    }

    @Override
    public void receiveMessage(Message message) {
        Intent intent = new Intent(this, MailActivity.class);
        intent.putExtra("key", (String) message.obj);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        listLoginInforFragment.updateUi();
        if (isLocked)
            startActivityForResult(new Intent(MailInforListActivity.this, LockScreenActivity.class), 1);
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
        if (ApplicationUtil.isApplicationInBackground(MailInforListActivity.this)) {
            isLocked = true;
        }
    }


}
