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
import com.example.larry_sea.norember.view.activity.storage_type_activity.WifiActivity;
import com.example.larry_sea.norember.view.fragment.list_storage_type_fragement.ListWifiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 9/28/2016.
 * <p>
 * 展示wifi list activity
 */

public class WifiInforListActivity extends AppCompatActivity implements FragmentInforActivityInterface {
    ListWifiFragment listWifiFragment;
    Toolbar mtoolbar;
    @BindView(R.id.id_activity_common_list_toolbar)
    Toolbar idActivityCommonListToolbar;
    boolean isLocked = false;                         //是否被锁屏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        listWifiFragment = new ListWifiFragment();
        listWifiFragment.setInforActivityInterface(this);
        idActivityCommonListToolbar.setTitle(R.string.wifi);
        idActivityCommonListToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        setSupportActionBar(idActivityCommonListToolbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_activity_common_list_container, listWifiFragment, "WifiInforListFragment");
        fragmentTransaction.commit();

    }

    @Override
    public void receiveMessage(Message message) {
        Intent intent = new Intent(this, WifiActivity.class);
        intent.putExtra("key", (String) message.obj);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        listWifiFragment.updateUi();
        if (isLocked)
            startActivityForResult(new Intent(WifiInforListActivity.this, LockScreenActivity.class), 1);
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
        if (ApplicationUtil.isApplicationInBackground(WifiInforListActivity.this)) {
            isLocked = true;
        }
    }


}
