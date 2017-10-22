package com.example.larry_sea.norember;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.larry_sea.norember.broadcast.ScreenLockBroadCastReceiver;
import com.example.larry_sea.norember.callback_interface.RecyclerviewClickInterface;
import com.example.larry_sea.norember.callback_interface.view_interface.MainActivityViewInterface;
import com.example.larry_sea.norember.entity.base_entity.StorageItemTypeEntity;
import com.example.larry_sea.norember.factory.IntentFactory;
import com.example.larry_sea.norember.presenter.MainAcitivityPresenter;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.utill.commonutils.CommonUtil;
import com.example.larry_sea.norember.utill.commonutils.StyleUtil;
import com.example.larry_sea.norember.view.activity.LockScreenActivity;
import com.example.larry_sea.norember.view.activity.SettingActivity;
import com.example.larry_sea.norember.view.customer.DialogPasswordGenerator;
import com.example.larry_sea.norember.view.fragment.Mainfragment;
import com.example.larry_sea.norember.view.fragment.NoContentFragment;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
*
* 应用的mainactivity
*
*
* */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityViewInterface, RecyclerviewClickInterface {

    @BindView(R.id.app_bar_main_action_menu)
    FloatingActionsMenu appBarMainActionMenu;
    boolean ISLIVE = false;                             //这个activity是否存活
    MainAcitivityPresenter mainAcitivityPresenter;
    static boolean isLocked = false;                           //是否被锁屏  true代表屏幕被锁
    final static int LOGIN_TYPE = 1;                    //代表登陆类型的type
    final static int CARD_TYPE = 2;                     //代表卡务类型的type
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    final static String TAG = MainActivity.class.toString();
    ScreenLockBroadCastReceiver screenLockBroadCastReceiver;

    static MainActivity mContext;

    /*
    *
    * 点击登录的监听器
    * */
    @OnClick(R.id.id_app_bar_action_menu_login_btn)
    void loginOnClick(View view) {
        startChooseActivity(LOGIN_TYPE);
    }

    /*
    *
    * 点击卡务的监听器
    * */
    @OnClick(R.id.id_app_bar_action_menu_bank_btn)
    void cardOnClick(View view) {
        startChooseActivity(CARD_TYPE);
    }


    /*
    * 点击安全笔记的监听器
    *
    * */
    @OnClick(R.id.id_app_bar_action_menu_safe_note_btn)
    void noteOnClick(View view) {
        startActivity(IntentFactory.getIntent(MainActivity.this, IntentFactory.SAFE_NOTE_INTENT));
        appBarMainActionMenu.collapse();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.initWindow(this, getWindow());
        setContentView(R.layout.activity_main);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        initView();
        mContext = this;


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.id_password_generator) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogPasswordGenerator dialogPasswordGenerator = new DialogPasswordGenerator();
            dialogPasswordGenerator.show(fragmentManager, "password_dialog");

        } else if (id == R.id.id_seeting) {
            startActivity(new Intent(this, SettingActivity.class));
        } /*else if (id == R.id.id_safe_folder) {
            startActivity(new Intent(this, SafeFolderActivity.class));

        } */ else if (id == R.id.id_recommend) {
            startActivity(CommonUtil.shareInfor(MainActivity.this, R.string.app_store_search_norember));

        } else if (id == R.id.id_feedback) {
            startActivity(IntentFactory.getIntent(MainActivity.this, IntentFactory.FEED_BACK_INTENT));
        } else if (id == R.id.id_exit) {
            this.finish();
            // 由左向右滑入的效果

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    *
    * 初始化view的相关工作
    *
    * */
    public void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        mainAcitivityPresenter = new MainAcitivityPresenter(this);
        mainAcitivityPresenter.initData();
        ISLIVE = true;
        initBroadCast();
    }

    @Override
    public void loadFragment(int fragmentType, List<StorageItemTypeEntity> storageItemList) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        if (fragmentType == 1 && storageItemList != null) {
            fragment = new Mainfragment();
            bundle.putSerializable("allTypeData", (Serializable) storageItemList);
        } else if (fragmentType == 0 && storageItemList == null) {
            fragment = new NoContentFragment();
            bundle.putString("noContentInfor", getString(R.string.no_password_tip));
        }
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.id_main_fragment_container, fragment, "mainFragmentTag").commitAllowingStateLoss();


    }


    /*
    *
    * 关闭realm
    * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainAcitivityPresenter.closeRealm();
        unregisterReceiver(screenLockBroadCastReceiver);


    }


    /*
    *
    * 打开相应的 list详细数据
    *
    * */
    @Override
    public void onClick(View view, int position) {
        startActivity(IntentFactory.getListIntent(MainActivity.this, position));

    }

    @Override
    public void onItemLongClick(View view, int position) {


    }

    @Override
    protected void onResume() {
        super.onResume();
        mainAcitivityPresenter.initData();
        if (isLocked) {
            startActivityForResult(new Intent(MainActivity.this, LockScreenActivity.class), 1);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ApplicationUtil.isApplicationInBackground(this) || ScreenLockBroadCastReceiver.isLocked) {
            isLocked = true;
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    /*
    *
    * 打开选择不同存储类型的activity
    *
    * */
    public void startChooseActivity(int categoryType) {
        Intent intent = IntentFactory.getIntent(MainActivity.this, IntentFactory.CHOOSE_STORAGE_TYPE_INTENT);
        intent.putExtra("type", categoryType);
        startActivity(intent);
        appBarMainActionMenu.collapse();
    }


    public void initBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        screenLockBroadCastReceiver = new ScreenLockBroadCastReceiver(MainActivity.this);
        this.registerReceiver(screenLockBroadCastReceiver, filter);


    }

    public static void finishThis() {
        mContext.finish();
    }

    /*
    *
    * 解锁activity
    *
    * */
    public static void delock() {
        isLocked = false;
    }

}
