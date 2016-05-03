package com.abitalo.www.rss_aggregator.view;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.view.MenuItem;

import com.abitalo.www.rss_aggregator.R;

/**
 * Created by Lancelot on 2016/5/4.
 */
public class AccountNavigationView extends NavigationView implements NavigationView.OnNavigationItemSelectedListener{


    AccountNavigationView(Context context) {
        this(context, null);
    }
    AccountNavigationView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    AccountNavigationView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        inflateHeaderView(R.layout.nav_header_main);
        inflateMenu(R.menu.activity_main_drawer);
        setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation com.abitalo.www.rss_aggregator.view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
