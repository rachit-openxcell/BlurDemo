package com.example.rachit.aemjdemo.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.example.rachit.aemjdemo.Fragment.AboutUs;
import com.example.rachit.aemjdemo.Fragment.ContentContainer;
import com.example.rachit.aemjdemo.Fragment.PrivacyPolicy;
import com.example.rachit.aemjdemo.Fragment.Terms;
import com.example.rachit.aemjdemo.R;
import com.example.rachit.aemjdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mBinding.drawerLayout, mBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
                mBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                mBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
            }
        };

        int width = (getResources().getDisplayMetrics().widthPixels)*3/5;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mBinding.navigation.getLayoutParams();
        params.width = width;
        mBinding.navigation.setLayoutParams(params);

        getSupportActionBar().setTitle(null);
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mBinding.toolbar.setNavigationIcon(null);
        mBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mBinding.navigation.setNavigationItemSelectedListener(this);
        mBinding.imgToolbar.setOnClickListener(this);

        initUI();
    }

    private void initUI() {

        mBinding.navigation.getMenu().getItem(0).setChecked(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, ContentContainer.newInstance()); // replace a Fragment with Frame Layout
        transaction.commit(); // commit the changes
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imgToolbar:
                mBinding.drawerLayout.openDrawer(Gravity.START);
                Log.e(TAG, "onClick: " );
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        Fragment fragment1 = null;

        switch (item.getItemId()) {
            case R.id.menu_home:
                if(!item.isChecked()) {
                    item.setChecked(true);
                    fragment = ContentContainer.newInstance();
                }
                else
                    mBinding.drawerLayout.closeDrawers(); // close the all open Drawer Views
                break;
            case R.id.menu_privacy_policy:
                if(!item.isChecked()) {
                    fragment1 = PrivacyPolicy.newInstance();
                    item.setChecked(true);
                }
                else
                    mBinding.drawerLayout.closeDrawers(); // close the all open Drawer Views
                break;
            case R.id.menu_terms:
                if(!item.isChecked()) {
                    fragment1 = Terms.newInstance();
                    item.setChecked(true);
                }
                else
                    mBinding.drawerLayout.closeDrawers(); // close the all open Drawer Views
                break;
            case R.id.menu_about_us:
                if(!item.isChecked()) {
                    fragment1 = AboutUs.newInstance();
                    item.setChecked(true);
                }
                else
                    mBinding.drawerLayout.closeDrawers(); // close the all open Drawer Views
                break;
        }
        if (fragment != null) {

            mBinding.contentFrame2.setVisibility(View.GONE);
            mBinding.contentFrame.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBinding.drawerLayout.closeDrawers(); // close the all open Drawer Views
                }
            }, 200);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment); // replace a Fragment with Frame Layout
            transaction.commit(); // commit the changes
            return true;
        } else if(fragment1 != null) {

            mBinding.contentFrame2.setVisibility(View.VISIBLE);
            mBinding.contentFrame.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBinding.drawerLayout.closeDrawers(); // close the all open Drawer Views
                }
            }, 200);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame_2, fragment1); // replace a Fragment with Frame Layout
            transaction.commit(); // commit the changes
        }
        return false;
    }


}

