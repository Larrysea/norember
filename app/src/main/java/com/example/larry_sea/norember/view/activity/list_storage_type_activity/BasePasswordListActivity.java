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
import com.example.larry_sea.norember.view.activity.PasswordActivity;
import com.example.larry_sea.norember.view.fragment.list_storage_type_fragement.ListBasePasswordFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 9/23/2016.
 * <p>
 * 展示basepassword list 的activity
 */
public class BasePasswordListActivity extends AppCompatActivity implements FragmentInforActivityInterface {


    ListBasePasswordFragment listBasePasswordFragment;
    @BindView(R.id.id_activity_base_password_list_toolbar)
    Toolbar idActivityBasePasswordListToolbar;
    boolean isLocked = false;                         //是否被锁屏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_password_list);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        listBasePasswordFragment = new ListBasePasswordFragment();
        idActivityBasePasswordListToolbar.setTitle(R.string.password);
        idActivityBasePasswordListToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        listBasePasswordFragment.setInforActivityInterface(this);
        setSupportActionBar(idActivityBasePasswordListToolbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_activity_base_password_list_container, listBasePasswordFragment, "BankListFragment");
        fragmentTransaction.commit();

    }

    /*
    * 接受listbase fragment 传过来来的消息
    *
    * */
    @Override
    public void receiveMessage(Message message) {
        Intent intent = new Intent(this, PasswordActivity.class);
        if (message.obj != null) {
            intent.putExtra("key", (String) message.obj);
            startActivity(intent);
        }
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
        listBasePasswordFragment.updateUi();
        if (isLocked)
            startActivityForResult(new Intent(BasePasswordListActivity.this, LockScreenActivity.class), 1);
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
        if (ApplicationUtil.isApplicationInBackground(BasePasswordListActivity.this)) {
            isLocked = true;
        }
    }


}
