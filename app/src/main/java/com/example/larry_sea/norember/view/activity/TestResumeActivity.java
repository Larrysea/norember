package com.example.larry_sea.norember.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.larry_sea.norember.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry-sea on 2016/10/17.
 * <p>
 * <p>
 * 测试resume
 */

public class TestResumeActivity extends AppCompatActivity {

    final static String TAG = TestResumeActivity.class.toString();
    @BindView(R.id.id_test_ed1)
    EditText idTestEd1;
    @BindView(R.id.id_test_ed2)
    EditText idTestEd2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        if (idTestEd1.getText() != null) {
            idTestEd1.setText("kong");

        }

        if (idTestEd2.getText() != null) {
            idTestEd2.setText("null");

            idTestEd2.getInputType();
            idTestEd2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }


}
