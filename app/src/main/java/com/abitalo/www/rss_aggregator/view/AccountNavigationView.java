package com.abitalo.www.rss_aggregator.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.abitalo.www.rss_aggregator.R;

/**
 * Created by Lancelot on 2016/5/4.
 * 登录后右侧的显示布局
 */
public class AccountNavigationView extends NavigationView implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private ImageView imageView;
    private Menu menu;
    private MenuItem exitChoice;
    private Context context;

    public AccountNavigationView(Context context) {
        this(context, null);
    }

    AccountNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    AccountNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflateHeaderView(R.layout.nav_header_main);
        inflateMenu(R.menu.activity_main_drawer);
        setNavigationItemSelectedListener(this);
        View headerView = getHeaderView(0);
        menu = getMenu();
        exitChoice = menu.getItem(0);
        exitChoice.setVisible(false);
        imageView = (ImageView) headerView.findViewById(R.id.exitIcon);
        imageView.setOnClickListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation com.abitalo.www.rss_aggregator.view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_logout:
                logout();
                 break;
        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        SharedPreferences mySharedPreferences= getContext().getSharedPreferences("userAuthentication",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("name", "");
        editor.apply();
     }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exitIcon:
                showExit();
                break;

        }
    }

    private void showExit() {
        exitChoice.setVisible(!exitChoice.isVisible());
       /* MenuItem myMoveGroupItem = menu.getItem(2);
        SubMenu subMenu = myMoveGroupItem.getSubMenu();
        subMenu.add("Item").setIcon(R.mipmap.rss);*/
    }
}
