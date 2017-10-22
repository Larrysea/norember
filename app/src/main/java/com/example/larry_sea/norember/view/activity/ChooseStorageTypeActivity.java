package com.example.larry_sea.norember.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.FragmentInforActivityInterface;
import com.example.larry_sea.norember.constants.ResourceConstants;
import com.example.larry_sea.norember.entity.base_entity.SmallTypeEntity;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.activity.storage_type_activity.BankCardActivity;
import com.example.larry_sea.norember.view.activity.storage_type_activity.CreditCardActivity;
import com.example.larry_sea.norember.view.activity.storage_type_activity.IdCardActivity;
import com.example.larry_sea.norember.view.activity.storage_type_activity.MailActivity;
import com.example.larry_sea.norember.view.activity.storage_type_activity.WifiActivity;
import com.example.larry_sea.norember.view.fragment.ChooseStorageTypeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 9/24/2016.
 * <p>
 * 选择具体存储类型的activity
 */

public class ChooseStorageTypeActivity extends AppCompatActivity implements FragmentInforActivityInterface {

    final static int BANKACTIVITY = 0;
    final static int CREDITCARDACTIVITY = 1;
    final static int IDCARDACTIVITY = 2;
    final static int WEBSITEACTIVITY = 0;
    final static int WIFIACTIVITY = 2;
    final static int MAILACTIVITY = 1;
    @BindView(R.id.id_activity_choose_storage_type_toolbar)
    Toolbar idActivityChooseStorageTypeToolbar;
    boolean isLocked = false;                         //是否被锁屏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_storage_type);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        initView();

    }

    /*
    * 初始化fragment
    *
    * */
    public void initView() {
        ChooseStorageTypeFragment chooseStorageTypeFragment = new ChooseStorageTypeFragment();
        idActivityChooseStorageTypeToolbar.setTitle(R.string.choose_type);
        idActivityChooseStorageTypeToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        setSupportActionBar(idActivityChooseStorageTypeToolbar);
        Bundle bundle = new Bundle();
        bundle.putSerializable("storageTypeData", getData(getIntent().getIntExtra("type", 0)));
        bundle.putInt("type", getIntent().getIntExtra("type", 0));         //获取类型
        chooseStorageTypeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.id_activity_choose_storage_type_container, chooseStorageTypeFragment, "chooseStorageFragment").commit();
    }


    /*
    * 接受相应的fragment中传递过来的消息
    *
    * */

    /*
       *
       * parm 1 代表是账户类
       *      2 代表是卡务类
       *
       * */
    public ArrayList<SmallTypeEntity> getData(int type) {

        ArrayList<SmallTypeEntity> result = new ArrayList<SmallTypeEntity>();
        SmallTypeEntity smallTypeEntity;
        switch (type) {

            case 1:
                for (int position = 0; position < ResourceConstants.loginNameResource.length; position++) {
                    smallTypeEntity = new SmallTypeEntity();
                    smallTypeEntity.setTypeName(getString(ResourceConstants.loginNameResource[position]));
                    smallTypeEntity.setIconSource(ResourceConstants.loginIconResource[position]);
                    result.add(smallTypeEntity);
                }
                break;
            case 2:
                for (int position = 0; position < ResourceConstants.cardNameResourece.length; position++) {
                    smallTypeEntity = new SmallTypeEntity();
                    smallTypeEntity.setTypeName(getString(ResourceConstants.cardNameResourece[position]));
                    smallTypeEntity.setIconSource(ResourceConstants.cardIconResource[position]);
                    result.add(smallTypeEntity);
                }
        }

        return result;


    }

    @Override
    public void receiveMessage(Message message) {
        Intent intent = new Intent();
        if (message.arg2 == 2) {
            switch (message.arg1) {
                case BANKACTIVITY:
                    intent.setClass(this, BankCardActivity.class);
                    break;
                case CREDITCARDACTIVITY:
                    intent.setClass(this, CreditCardActivity.class);
                    break;

                case IDCARDACTIVITY:
                    intent.setClass(this, IdCardActivity.class);
                    break;
            }
        } else if (message.arg2 == 1) {
            switch (message.arg1) {
                case WEBSITEACTIVITY:
                    intent.setClass(this, AddWebSiteActivity.class);
                    break;
                case MAILACTIVITY:
                    intent.setClass(this, MailActivity.class);
                    break;
                case WIFIACTIVITY:
                    intent.setClass(this, WifiActivity.class);
                    break;
            }
        }
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(ChooseStorageTypeActivity.this, LockScreenActivity.class), 1);
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
        if (ApplicationUtil.isApplicationInBackground(ChooseStorageTypeActivity.this)) {
            isLocked = true;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



}
