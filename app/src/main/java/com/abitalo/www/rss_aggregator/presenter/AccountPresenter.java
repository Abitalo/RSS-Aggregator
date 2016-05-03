package com.abitalo.www.rss_aggregator.presenter;

import com.abitalo.www.rss_aggregator.view.IAccountView;

/**
 * Created by Lancelot on 2016/5/4.
 */
public class AccountPresenter {
    IAccountView iAccountView=null;
    public AccountPresenter(IAccountView accountNavicationView){
        iAccountView=accountNavicationView;
    }
    public boolean login(){
        //TODO
        return isValidated(iAccountView.getUserName(),iAccountView.getPassword());
    }
    public boolean isValidated(String username,String password){
        //TODO remote database
        //下面是测试
        if(username!=null){
            iAccountView.showSuccessHint();}
        else{
            iAccountView.showWarning("empty!");}
        return true;
    }
}
