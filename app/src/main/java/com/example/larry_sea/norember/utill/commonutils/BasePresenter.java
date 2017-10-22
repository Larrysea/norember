package com.example.larry_sea.norember.utill.commonutils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Larry-sea on 2016/9/14.
 * <p>
 * 抽象的presenter类
 */
public abstract class BasePresenter<T> {
    protected Reference<T> mviewRef;  //视图的引用

    /*
    *
    * 将视图与presenter绑定
    * */
    public void attachView(T view) {
        mviewRef = new WeakReference<T>(view);
    }

    protected T getView() {
        return mviewRef.get();
    }

    public void detachView() {

        if (mviewRef != null) {
            mviewRef.clear();
            mviewRef = null;
        }
    }

    public boolean isViewAttached() {
        return mviewRef != null && mviewRef.get() != null;
    }


}
