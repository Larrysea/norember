package com.example.larry_sea.norember.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.larry_sea.norember.R;


/**
 * Created by Larry on 5/13/2016.
 * <p>
 * <p>
 * 展示没有资源时的fragment
 */
public class NoContentFragment extends Fragment {

    TextView textview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String contentindfor = bundle.getString("noContentInfor");
        View view = inflater.inflate(R.layout.fragement_no_content, container, false);
        textview = (TextView) view.findViewById(R.id.id_no_content_text_view);
        textview.setText(contentindfor);
        return view;

    }


}
