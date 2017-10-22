package com.example.larry_sea.norember.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.larry_sea.norember.R;
import com.example.larry_sea.norember.entity.base_entity.FeedBack;
import com.example.larry_sea.norember.utill.commonutils.DeviceUtil;
import com.example.larry_sea.norember.utill.internet_util.InternetUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Larry-sea on 10/4/2016.
 * <p>
 * 反馈模块fragment
 */

public class FeedbackFragment extends Fragment {


    FeedBack mfeedBack;        //反馈内容信息类
    @BindView(R.id.id_feedback_content)
    EditText idFeedbackContent;
    @BindView(R.id.id_feedback_email_edittext)
    EditText idFeedbackEmailEdittext;
    @BindView(R.id.id_feedback_send_button)
    Button idFeedbackSendButton;

    @OnClick(R.id.id_feedback_send_button)
    void submitFeedback(View view) {
        InternetUtil internetUtil = new InternetUtil();
        initFeedbackInfo();
        internetUtil.submitFeedback(getActivity(), mfeedBack);
        ((AppCompatActivity) getActivity()).onBackPressed();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public void initFeedbackInfo() {
        if (mfeedBack == null)
            mfeedBack = new FeedBack();
        if (!idFeedbackContent.getText().toString().isEmpty()) {
            mfeedBack.setFeedbackContent(idFeedbackContent.getText().toString());
        } else {
            Toast.makeText(getActivity(), R.string.feed_back_content_cant_null, Toast.LENGTH_SHORT).show();
        }
        if (!idFeedbackEmailEdittext.getText().toString().isEmpty()) {
            mfeedBack.setContectWay(idFeedbackEmailEdittext.getText().toString());
        } else {
            mfeedBack.setContectWay(getResources().getString(R.string.no_contect_way));
        }
        mfeedBack.setOsInfo("system version" + Build.VERSION.SDK_INT + "phone modeal " + Build.MODEL + "screen size " + DeviceUtil.getScreenSizeOfDevice2(getActivity()));
    }
}
