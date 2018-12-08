package com.avardonigltd.mobilemedicalaid.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.fragment.Terms;
import com.avardonigltd.mobilemedicalaid.fragment.UpdateUserProfileFragment;
import com.avardonigltd.mobilemedicalaid.fragment.user_dashboard;
import com.avardonigltd.mobilemedicalaid.interfaces.Listeners;

public class NavigationalDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Listeners{

    private SharedPreferences sharedPreferences;
    private NavigationView nvDrawer;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigational_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        selectedFragment = new user_dashboard();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_drawer_frame_layout,selectedFragment)
                .commit();

        nvDrawer = findViewById(R.id.nvView);
        nvDrawer.setNavigationItemSelectedListener(this);

    }

    public void fragmentSelected(@NonNull final Fragment fragment){
        final FragmentManager manager =  getSupportFragmentManager();
        manager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.nav_drawer_frame_layout,fragment)
                .commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        Toolbar toolbar = findViewById(R.id.toolbar);
        switch(item.getItemId()){
            case R.id.dashboard:
                selectedFragment = new user_dashboard();
                toolbar.setTitle("DASHBOARD");
                break;

            case R.id.kyc:
                intent = new Intent(this, KYC.class);
                startActivity(intent);
                break;

            case R.id.terms:
                selectedFragment = new Terms();
                toolbar.setTitle("TERMS & Conditions");
                break;

            case R.id.update_profile:
                selectedFragment = new UpdateUserProfileFragment();
                toolbar.setTitle("PROFILE");
                break;

//            case R.id.settings:
//                intent = new Intent(this, Settings.class);
//                startActivity(intent);
//                break;

            case R.id.logout:
                intent = new Intent(this, Login.class);
                startActivity(intent);
                break;
        }


        mDrawer.closeDrawer(GravityCompat.START);
        mDrawer.setDrawerElevation(0);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //Set your new fragment here
                if (selectedFragment != null) {
                    fragmentSelected(selectedFragment);
                }
            }
        });
        return true;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
}

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
