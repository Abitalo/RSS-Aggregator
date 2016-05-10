package com.abitalo.www.rss_aggregator.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abitalo.www.rss_aggregator.R;

/**
 * Created by sangzhenya on 2016/5/8.
 * 找回密码界面
 */
public class ForgetPasswordView extends Fragment implements View.OnClickListener {
    Context context = null;
    View view = null;

    EditText etUserEmail = null;
    Button etFindPassword = null;
    TextView tvBack2Login = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forget_password_main, container, false);
        initView();
        return view;
    }

    private void initView() {
        etUserEmail = (EditText) view.findViewById(R.id.edit_user_name);
        etFindPassword = (Button) view.findViewById(R.id.forget_send);
        tvBack2Login = (TextView) view.findViewById(R.id.forget_login);

        etFindPassword.setOnClickListener(this);
        tvBack2Login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_login:
                gotoLogin();
                break;
            case R.id.forget_send:
                findPassword();
                break;
        }
    }

    private void findPassword() {
        //// TODO:添加找回密码操作 2016/5/9
    }

    private void gotoLogin() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_content, new SignInView(), "sign_in").commit();
    }
}
