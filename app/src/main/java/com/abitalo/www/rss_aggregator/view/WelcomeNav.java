package com.abitalo.www.rss_aggregator.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.presenter.AccountPresenter;
import com.abitalo.www.rss_aggregator.util.MD5Encrypt;

/**
 * Created by Lancelot on 2016/5/3.
 */
public class WelcomeNav extends Fragment implements View.OnClickListener,IAccountView{
    AccountPresenter presenter=null;

    View view = null;
    EditText username=null;
    EditText password=null;
    Button btnLoginSubmit = null;
    Button btnRegisterSubmit=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nav_account,container,false);
        initialView();
        return view;
    }

    private void initialView(){
        username=(EditText) view.findViewById(R.id.edit_login_username);
        password=(EditText) view.findViewById(R.id.edit_login_password);

        btnLoginSubmit=(Button) view.findViewById(R.id.btn_login_submit);
        btnLoginSubmit.setOnClickListener(this);

        btnRegisterSubmit=(Button)view.findViewById(R.id.btn_login_register);
        btnRegisterSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_login_submit){
            presenter=new AccountPresenter(this);
            presenter.login();
        }else if(v.getId()==R.id.btn_login_register){
            presenter=new AccountPresenter(this);
            presenter.register();
        }
    }

    private void showMenu(){//将来需要使用从云端获得的用户数据作为参数，然后使用adapter根据用户数据填充菜单。。
        NavigationView navigationView=(NavigationView)getActivity().findViewById(R.id.nav_account);
        navigationView.addView(new AccountNavigationView(getActivity()));
        onDestroy();
    }
    @Override
    public String getUserName() {
        return username.getText().toString();
    }

    @Override
    public String getPassword() {
        return MD5Encrypt.parse(password.getText().toString());
    }

    @Override
    public boolean onFailure(String msg) {
        //getActivity().findViewById(R.id.content_coordinator
        //用上面的可以滑掉，但不会显示在最上层
        Snackbar.make(view,msg,Snackbar.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onSuccess() {
        showMenu();
        Snackbar.make(view,"Success!",Snackbar.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
}
