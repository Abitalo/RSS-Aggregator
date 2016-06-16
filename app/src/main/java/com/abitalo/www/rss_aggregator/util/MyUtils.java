package com.abitalo.www.rss_aggregator.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by sangz on 2016/6/7.
 */
public class MyUtils {
    public static Drawable getDrawable(Context context, int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(res, context.getTheme());
        } else {
            return context.getResources().getDrawable(res);
        }
    }
}
