package com.abitalo.www.rss_aggregator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.abitalo.www.rss_aggregator.helper.FavoriteHelper;

import java.util.HashMap;

public class WebActivity extends AppCompatActivity{
    private WebView webView = null;
    private Toolbar toolbar = null;
    private TextView title = null;
    private TextView time = null;
    private TextView author = null;
    private Button favorite = null;
    private Integer facetId =null;
    private final FavoriteHelper favoriteHelper=new FavoriteHelper();
    private final LikedState state=new LikedState();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_rss_detail);
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.title);
        time = (TextView) findViewById(R.id.time);
        webView = (WebView) findViewById(R.id.web_view);
        toolbar = (Toolbar) findViewById(R.id.web_tool_bar);
        author = (TextView) findViewById(R.id.author);
        favorite = (Button) findViewById(R.id.favorite_btn);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        HashMap item = (HashMap) intent.getSerializableExtra("item");
        facetId =intent.getIntExtra("facetId",0);

        String titleText=item.get("title").toString();
        String pubDateText=item.get("date").toString();
        String description = (String) item.get("description");
        String encoded = (String) item.get("encoded");
        String authorstr = (String) item.get("author");
        String html;
        if(null != encoded)
            html=encoded;
        else
            html=description;

        if(null != authorstr){
            authorstr=authorstr.toString();
            author.setText("by "+authorstr);
        }
        else {
            author.setText("");
        }

        title.setText(titleText);
        time.setText(pubDateText);

        showWebView(html);
        initFavoriteHelper(titleText,authorstr,html);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state.isLiked()){
                    favorite.setBackgroundColor(getResources().getColor(R.color.white));
                    favorite.setText("+like");
                    favorite.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    favoriteHelper.query(FavoriteHelper.DELETE);
                }else{
                    favorite.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    favorite.setText("liked");
                    favorite.setTextColor(getResources().getColor(R.color.white));
                    favoriteHelper.query(FavoriteHelper.ADD);
                }
            }
        });
    }

    private void showWebView(String html) {
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
    }

    private void initFavoriteHelper(final String title, final String author, final String content){
        favoriteHelper.init(WebActivity.this,title,author,content,facetId);
        favoriteHelper.query(FavoriteHelper.CHECK);
    }

    public void onQueryFinished(final boolean liked){
        state.setLiked(liked);
        if(liked){
            favorite.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            favorite.setText("liked");
            favorite.setTextColor(getResources().getColor(R.color.white));
        }else{
            favorite.setBackgroundColor(getResources().getColor(R.color.white));
            favorite.setText("+like");
            favorite.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private class LikedState{
        boolean liked;
        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rss_web_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
