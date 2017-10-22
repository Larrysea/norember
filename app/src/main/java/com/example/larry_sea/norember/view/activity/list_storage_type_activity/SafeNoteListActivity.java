package com.example.larry_sea.norember.view.activity.list_storage_type_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.activity.LockScreenActivity;
import com.example.larry_sea.norember.view.activity.storage_type_activity.SafeNoteActivity;
import com.example.larry_sea.norember.view.fragment.list_storage_type_fragement.ListSafeNoteFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 10/9/2016.
 * <p>
 * <p>
 * 展示listsafenote的activity
 */

public class SafeNoteListActivity extends AppCompatActivity implements FragmentInforActivityInterface {
    ListSafeNoteFragment listSafeNoteFragment;
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
        listSafeNoteFragment = new ListSafeNoteFragment();
        listSafeNoteFragment.setInforActivityInterface(this);
        idActivityCommonListToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        idActivityCommonListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SafeNoteListActivity.this.finish();
            }
        });
        idActivityCommonListToolbar.setTitle(R.string.safe_note);
        getSupportFragmentManager().beginTransaction().add(R.id.id_activity_common_list_container, listSafeNoteFragment, "listSafeNoteFragment").commit();

    }

    /*
    * 接受fragment中传递过来的消息进行处理
    *
    * */
    @Override
    public void receiveMessage(Message message) {

        if (message != null) {
            Intent intent = new Intent(this, SafeNoteActivity.class);
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
        listSafeNoteFragment.updateUi();
        if (isLocked)
            startActivityForResult(new Intent(SafeNoteListActivity.this, LockScreenActivity.class), 1);
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
        if (ApplicationUtil.isApplicationInBackground(SafeNoteListActivity.this)) {
            isLocked = true;
        }
    }

}
