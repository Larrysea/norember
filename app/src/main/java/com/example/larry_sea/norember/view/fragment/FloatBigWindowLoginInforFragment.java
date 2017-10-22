package com.example.larry_sea.norember.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.larry_sea.norember.R;

/**
 * Created by Larry-sea on 10/3/2016.
 * <p>
 * float big view 显示其中的登陆信息的fragment
 */

public class FloatBigWindowLoginInforFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_big_window_login_infor, container, false);
        return view;
    }


}
