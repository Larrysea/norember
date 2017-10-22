package com.example.larry_sea.norember.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.callback_interface.RecyclerviewClickInterface;
import com.example.larry_sea.norember.utill.commonutils.ApplicationUtil;
import com.example.larry_sea.norember.view.fragment.AddWebSiteFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry-sea on 2016/9/7.
 * <p>
 * 添加网址的activity
 */
public class AddWebSiteActivity extends AppCompatActivity implements RecyclerviewClickInterface, AddWebSiteFragment.transferData {

    final static String fragmenttag = "websitetypefragment";    //fragmenttag标签
    AddWebSiteFragment maddWebSiteFragment;                //添加fragment
    @BindView(R.id.id_activity_add_web_site_urlname_tv)
    EditText idActivityAddWebSiteUrlnameTv;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Intent mintent;
    @BindView(R.id.id_fragment_add_web_site_toolbar)
    Toolbar idFragmentAddWebSiteToolbar;
    boolean isLocked = false;       //是否锁住

    @OnClick(R.id.id_activity_add_web_site_next_step)
    void onClick(View view) {
        Intent intent = new Intent(this, AddWebSiteInforActivity.class);
        Bundle bundle = new Bundle();
        if (!idActivityAddWebSiteUrlnameTv.getText().toString().isEmpty()) {
            bundle.putString("web_site_url", idActivityAddWebSiteUrlnameTv.getText().toString());
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_web_site);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);
        maddWebSiteFragment = new AddWebSiteFragment();
        fragmentManager = getSupportFragmentManager();
        idFragmentAddWebSiteToolbar.setNavigationIcon(R.mipmap.ic_navigation_small);
        idFragmentAddWebSiteToolbar.setTitle(R.string.web_site);
        setSupportActionBar(idFragmentAddWebSiteToolbar);
        idFragmentAddWebSiteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inforClose();
            }
        });
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_activity_add_web_site_container, maddWebSiteFragment, fragmenttag);
        fragmentTransaction.commit();


    }

    @Override
    public void transferData(Bundle webSiteInfor) {
        if (mintent == null) {
            mintent = new Intent(this, AddWebSiteInforActivity.class);
        }
        mintent.putExtras(webSiteInfor);
    }

    @Override
    public void onClick(View view, int position) {
        if (mintent != null)
            startActivity(mintent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }


    /*
    * 关闭this activity
    *
    * */
    public void inforClose() {
        this.finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (ApplicationUtil.isApplicationInBackground(AddWebSiteActivity.this)) {
            isLocked = true;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isLocked)
            startActivityForResult(new Intent(AddWebSiteActivity.this, LockScreenActivity.class), 1);
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


}
