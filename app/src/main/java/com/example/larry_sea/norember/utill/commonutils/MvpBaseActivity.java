package com.example.larry_sea.norember.utill.commonutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Larry-sea on 2016/9/15.
 * 基础的mvp activity
 */
public abstract class MvpBaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    protected T presenter;

    protected abstract T createPrensenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPrensenter();
        presenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
