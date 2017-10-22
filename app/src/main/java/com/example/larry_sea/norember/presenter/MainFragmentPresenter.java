package com.example.larry_sea.norember.presenter;

import com.example.larry_sea.norember.callback_interface.view_interface.MainFragmentViewInterface;

/**
 * Created by Larry-sea on 2016/9/16.'
 * <p>
 * <p>
 * mainfragment的中介
 */
public class MainFragmentPresenter {
    MainFragmentViewInterface mainFragmentViewInterface;

    public MainFragmentPresenter(MainFragmentViewInterface mainFragmentViewInterface) {
        this.mainFragmentViewInterface = mainFragmentViewInterface;
    }

    public void init() {
        // mainFragmentViewInterface.loading();
    }

    /*
    * 消失progressview
    *
    * */
    public void dismisProgressView() {

        // mainFragmentViewInterface.disLoading();
    }


}
