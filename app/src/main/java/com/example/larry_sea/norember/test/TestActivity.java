package com.example.larry_sea.norember.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.larry_sea.norember.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 2016/10/30.
 * <p>
 * 使用秘钥
 */

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.id_activity_base_toolbar)
    Toolbar idActivityBaseToolbar;
    BasicAndroidKeyStoreFragment basicAndroidKeyStoreFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        idActivityBaseToolbar.setTitle("测试keyStore");
        setSupportActionBar(idActivityBaseToolbar);
        basicAndroidKeyStoreFragment = new BasicAndroidKeyStoreFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.id_activity_base_container, basicAndroidKeyStoreFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.key_store_fragment_menu, menu);
        return true;
    }
}
