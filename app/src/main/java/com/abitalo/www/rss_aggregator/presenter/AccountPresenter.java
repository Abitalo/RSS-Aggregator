package com.abitalo.www.rss_aggregator.presenter;

import com.abitalo.www.rss_aggregator.helper.RegisterHelper;
import com.abitalo.www.rss_aggregator.view.IAccountView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Lancelot on 2016/5/4.
 * 登录代理者
 */
public class AccountPresenter {
    private IAccountView iAccountView=null;

    public AccountPresenter(IAccountView accountNavicationView){
        iAccountView=accountNavicationView;
    }

    public void login(){
        loginValidated(iAccountView.getUserName(),iAccountView.getPassword());
    }

    public void register(){
        registerValidated(iAccountView.getUserName(),iAccountView.getPassword(), iAccountView.getEmail());
    }

    private void loginValidated(String username, String password) {
        final BmobUser user = new BmobUser();
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

    private void registerValidated(final String username, String password, String email) {
        BmobUser user = new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUp(iAccountView.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                RegisterHelper registerHelper = new RegisterHelper(iAccountView.getContext(), username);
                registerHelper.getUser();
                iAccountView.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                iAccountView.onFailure(s);
            }
        });



    }
}
