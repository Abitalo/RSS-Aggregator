package com.abitalo.www.rss_aggregator.view;

import android.content.Context;

/**
 * Created by Lancelot on 2016/5/4.
 */
public interface IAccountView {
    Context getContext();
    String getUserName();
    String getPassword();
    boolean onFailure(String msg);
    boolean onSuccess();
}
