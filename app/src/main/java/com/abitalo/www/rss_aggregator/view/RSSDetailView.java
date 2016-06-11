package com.abitalo.www.rss_aggregator.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.abitalo.www.rss_aggregator.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class RSSDetailView extends Fragment {
    private View view;
    private WebView webView = null;
    private Toolbar toolbar = null;
    private TextView title = null;
    private TextView time = null;
    private TextView author = null;
    private Date pubDate= null;
    private Date currentDate = null;
    private Bundle args = null;
    private final DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_rss_detail,container,false);
        Log.e("LOGCAT", "老子创建Detail！！！！！！！！！！！！！！！！！！！！！");
        initView();
        return  view;
    }

    public static RSSDetailView getInstance(Bundle bundle) {
        RSSDetailView rssDetailView = new RSSDetailView();
        rssDetailView.setArguments(bundle);
        return rssDetailView;
    }

    public void setArguments(Bundle args) {
        this.args=args;
    }

    private void initView(){
        title = (TextView) view.findViewById(R.id.title);
        time = (TextView) view.findViewById(R.id.time);
        webView = (WebView) view.findViewById(R.id.web_view);
        toolbar = (Toolbar) view.findViewById(R.id.web_tool_bar);
        author = (TextView) view.findViewById(R.id.author);
        HashMap item = (HashMap) args.getSerializable("item");
        if(null == title){
            Log.e("LOGCAT", "操你妈！！！！！！！！！！！！！！！！！！！！！");
        }
        title.setText(item.get("title").toString());
        time.setText(item.get("date").toString());

        String description = (String) item.get("description");
        String encoded = (String) item.get("encoded");
        String authorstr = (String) item.get("author");

        if(null != authorstr){
            authorstr=authorstr.toString();
            author.setText("by "+authorstr);
        }
        else {
            author.setText("");
        }

        if(null != encoded)
            showWebView(encoded);
        else
            showWebView(description);
    }

    private void showWebView(String html)
    {
        String CSS_STYLE ="<style>* {color:#808080;max-width: 100% !important;word-wrap:break-word !important;} " +
                                    "img {display: inline !important; height: auto !important; max-width: 100% !important;border-radius :6px;box-shadow: 0px 1px 3px rgba(0,0,0,0.12), 0px 1px 2px rgba(0,0,0,0.24);} " +
                                    "a {display:inline-block !important; height: auto !important;max-width: 100% !important; word-wrap:break-word !important;" +
                                    "table{ width:100%25 !important; word-wrap:break-word !important;} " +
                                    "pre{display:block !important; height: auto !important;max-width: 100% !important; word-wrap:break-word !important;}" +
                                    "td{max-width: 100% !important; word-wrap:break-word !important;</style>";
        webView.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        webView.getSettings();
        // 设置WevView要显示的网页
        webView.loadDataWithBaseURL(null, CSS_STYLE+html, "text/html", "utf-8",
                null);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webView.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。

        //                点击链接由自己处理，而不是新开Android的系统browser响应该链接。
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //设置点击网页里面的链接还是在当前的webview里跳转
                view.loadUrl(url);
                return true;
            }
        });
    }

}
