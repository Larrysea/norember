package com.example.larry_sea.norember.view.activity.list_storage_type_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.activity.LockScreenActivity;
import com.example.larry_sea.norember.view.activity.storage_type_activity.LoginInforActivity;
import com.example.larry_sea.norember.view.fragment.list_storage_type_fragement.ListLoginInforFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 9/28/2016.
 * <p>
 * 显示登陆信息的loginfor list activity
 */

public class LoginInforListActivity extends AppCompatActivity implements FragmentInforActivityInterface {
    ListLoginInforFragment listLoginInforFragment;
    @BindView(R.id.id_activity_common_list_toolbar)
    Toolbar idActivityCommonListToolbar;
    boolean isLocked = false;                         //是否被锁屏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        idActivityCommonListToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        idActivityCommonListToolbar.setTitle(R.string.web_site);
        setSupportActionBar(idActivityCommonListToolbar);
        listLoginInforFragment = new ListLoginInforFragment();
        listLoginInforFragment.setInforActivityInterface(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_activity_common_list_container, listLoginInforFragment, "BankListFragment");
        fragmentTransaction.commit();

    }

    @Override
    public void receiveMessage(Message message) {
        Intent intent = new Intent(this, LoginInforActivity.class);
        intent.putExtra("key", (String) message.obj);
        startActivity(intent);
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

    @Override
    public void onResume() {
        super.onResume();
        listLoginInforFragment.updateUi();
        if (isLocked)
            startActivityForResult(new Intent(LoginInforListActivity.this, LockScreenActivity.class), 1);
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
        if (ApplicationUtil.isApplicationInBackground(LoginInforListActivity.this)) {
            isLocked = true;
        }
    }


}
