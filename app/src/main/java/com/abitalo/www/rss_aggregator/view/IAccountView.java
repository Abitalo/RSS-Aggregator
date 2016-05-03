package com.abitalo.www.rss_aggregator.view;

/**
 * Created by Lancelot on 2016/5/4.
 */
public interface IAccountView {
    String getUserName();
    String getPassword();
    boolean showWarning(String msg);
    boolean showSuccessHint();
}
