package com.shareme.bank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nex3z.notificationbadge.NotificationBadge;
import com.shareme.bank.fragments.AboutApp;
import com.shareme.bank.fragments.contact_us.Contact_usFragment;
import com.shareme.bank.fragments.setting.Notify_SettingFragment;
import com.shareme.bank.fragments.favor_fragment.FavoriteFragment;
import com.shareme.bank.fragments.home_fragments.create_donation_request.Create_Donation_Request;
import com.shareme.bank.fragments.home_fragments.HomeFragment;
import com.shareme.bank.fragments.InfoFragment;
import com.shareme.bank.fragments.notify_fragment.NotifyFragment;
import com.shareme.bank.fragments.report_fragment.ReportFragment;
import com.shareme.bank.login.LoginActivity;
import com.shareme.bank.notify_count.API_NotifyCout;
import com.shareme.bank.notify_count.NotifyCountResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Fragment fragment = null;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private NotificationBadge notificationBadge;
    private int count = 0;
    private SharedPreferences preferences;
    private String api_token;
    private Toolbar toolbar;
    private TextView title;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = MainActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token =preferences.getString("api_token","");

        title = findViewById(R.id.toolbar_title);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.mipmap.humburger);
        toolbar.setOverflowIcon(drawable);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new Create_Donation_Request();
                displaySelectedFragment(fragment);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        notificationBadge = findViewById(R.id.badge);
        notificationBadge.setNumber(++count);
        ImageView notification_icon = findViewById(R.id.notification_icon);

        notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                notificationBadge.setNumber(count);

                removeNotifivcatio();

                fragment = new NotifyFragment();
                displaySelectedFragment(fragment);
                fab.setVisibility(View.INVISIBLE);

            }
        });


        //Select Home by default
        navigationView.setCheckedItem(R.id.home);
        Fragment fragment = new HomeFragment();
        displaySelectedFragment(fragment);


        setupBadge();

    }



    private void setupBadge() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_NotifyCout api_notifyCout = retrofit.create(API_NotifyCout.class);
        Call<NotifyCountResponse> call = api_notifyCout.getNotifyCount(api_token);
        call.enqueue(new Callback<NotifyCountResponse>() {
            @Override
            public void onResponse(Call<NotifyCountResponse> call, Response<NotifyCountResponse> response) {

                 count = response.body().getData().getNotificationsCount();

            }
            @Override
            public void onFailure(Call<NotifyCountResponse> call, Throwable t) {
            }
        });
    }


    private void removeNotifivcatio() {


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressLint("RestrictedApi")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            fragment = new HomeFragment();
            displaySelectedFragment(fragment);
            fab.setVisibility(View.VISIBLE);
            title.setText("");

        } else if (id == R.id.nav_favorite) {

            fragment = new FavoriteFragment();
            displaySelectedFragment(fragment);
            fab.setVisibility(View.INVISIBLE);
            title.setText("Favorite");

        } else if (id == R.id.nav_notify) {

            fragment = new Notify_SettingFragment();
            displaySelectedFragment(fragment);
            fab.setVisibility(View.INVISIBLE);
            title.setText("Setting");

        } else if (id == R.id.nav_contactus) {

            fragment = new Contact_usFragment();
            displaySelectedFragment(fragment);
            fab.setVisibility(View.INVISIBLE);
            title.setText("Contact US");

        } else if (id == R.id.nav_report) {

            fragment = new ReportFragment();
            displaySelectedFragment(fragment);
            fab.setVisibility(View.INVISIBLE);
            title.setText("Report");

        } else if (id == R.id.nav_exit) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("loggedIn" , false ).apply();

            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }else if (id == R.id.nav_info) {

            fragment = new InfoFragment();
            displaySelectedFragment(fragment);
            fab.setVisibility(View.INVISIBLE);

        }else if (id == R.id.nav_about_app) {

            fragment = new AboutApp();
            displaySelectedFragment(fragment);
            fab.setVisibility(View.INVISIBLE);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }


}
