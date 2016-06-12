package com.abitalo.www.rss_aggregator.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abitalo.www.rss_aggregator.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;

/**
 * Created by sangzhenya on 2016/5/8.
 * 找回密码界面
 */
public class ForgetPasswordView extends Fragment implements View.OnClickListener {
    private View view = null;

    private EditText etUserEmail = null;
    private Button etFindPassword = null;
    private TextView tvBack2Login = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forget_password_main, container, false);
        initView();
        return view;
    }

    private void initView() {
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("找回密码");
        } catch (Exception exception) {
            Log.e("RssListView", "Some thing wrong");
        }
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
        BmobUser.resetPasswordByEmail(getContext(), etUserEmail.getText().toString(), new ResetPasswordByEmailListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "邮件发送成功，请按照邮箱提示操作！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("ForgetPasswordView", "code:"+i);
                Log.i("ForgetPasswordView", "code:"+s);
            }
        });
    }

    private void gotoLogin() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_content, new SignInView(), "sign_in").commit();
    }
}
