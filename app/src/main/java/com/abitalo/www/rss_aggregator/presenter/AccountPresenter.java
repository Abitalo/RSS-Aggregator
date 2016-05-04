package com.abitalo.www.rss_aggregator.presenter;

import com.abitalo.www.rss_aggregator.view.IAccountView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Lancelot on 2016/5/4.
 */
public class AccountPresenter {
    IAccountView iAccountView=null;

    public AccountPresenter(IAccountView accountNavicationView){
        iAccountView=accountNavicationView;
    }

    public void login(){
        loginValidated(iAccountView.getUserName(),iAccountView.getPassword());
    }

    public void register(){
        registerValidated(iAccountView.getUserName(),iAccountView.getPassword());
    }

    private void loginValidated(String username, String password) {
        BmobUser user = new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(iAccountView.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                iAccountView.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                iAccountView.onFailure(s);
            }
        });
    }

    private void registerValidated(String username, String password) {
        BmobUser user = new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(iAccountView.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                iAccountView.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                iAccountView.onFailure(s);
            }
        });
    }
}
