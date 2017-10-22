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
import android.view.View;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.activity.LockScreenActivity;
import com.example.larry_sea.norember.view.activity.storage_type_activity.IdCardActivity;
import com.example.larry_sea.norember.view.fragment.list_storage_type_fragement.ListIdCardFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 9/28/2016.
 * <p>
 * <p>
 * idcard list activity 展示身份证信息的activity
 */

public class IdCardListActivity extends AppCompatActivity implements FragmentInforActivityInterface {

    ListIdCardFragment listidCardFragment;
    @BindView(R.id.id_activity_common_list_toolbar)
    Toolbar idActivityCommonListToolbar;
    boolean isLocked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        listidCardFragment = new ListIdCardFragment();
        listidCardFragment.setInforActivityInterface(this);
        idActivityCommonListToolbar.setTitle(R.string.id_card);
        idActivityCommonListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        idActivityCommonListToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        setSupportActionBar(idActivityCommonListToolbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_activity_common_list_container, listidCardFragment, "IdCardListFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void receiveMessage(Message message) {
        Intent intent = new Intent(this, IdCardActivity.class);
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
        listidCardFragment.updateUi();
        if (isLocked)
            startActivityForResult(new Intent(IdCardListActivity.this, LockScreenActivity.class), 1);
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
        if (ApplicationUtil.isApplicationInBackground(IdCardListActivity.this)) {
            isLocked = true;
        }
    }

}
