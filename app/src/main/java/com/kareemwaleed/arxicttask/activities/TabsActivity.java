package com.kareemwaleed.arxicttask.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.kareemwaleed.arxicttask.R;
import com.kareemwaleed.arxicttask.adapters.ViewPagerAdapter;
import com.kareemwaleed.arxicttask.fragments.ContactsListFragment;
import com.kareemwaleed.arxicttask.fragments.JsonListFragment;

public class TabsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences user;
    private final int CONTACTS_PERMISSION_REQUEST = 0;
    private boolean permissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int isPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (isPermissionGranted == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true;
        } else {
            permissionGranted = false;
            ActivityCompat.requestPermissions(this
                    , new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_PERMISSION_REQUEST);
        }
        setContentView(R.layout.activity_tabs);
        user = getSharedPreferences("user", MODE_PRIVATE);
        initViewVars();
        setSupportActionBar(toolbar);
        if (permissionGranted) {
            setupViewPager();
            tabLayout.setupWithViewPager(viewPager);
            setupTabsIcons();
        }
    }

    private void initViewVars() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putBoolean("permission", permissionGranted);
        ContactsListFragment temp = new ContactsListFragment();
        temp.setArguments(bundle);
        viewPagerAdapter.addFragment(temp, "Contact List");
        viewPagerAdapter.addFragment(new JsonListFragment(), "Json List");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupTabsIcons() {
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_phone_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_file_download_white_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tabs_layout_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                user.edit().clear().apply();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CONTACTS_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    setupViewPager();
                    tabLayout.setupWithViewPager(viewPager);
                    setupTabsIcons();
                } else {
                    permissionGranted = false;
                    setupViewPager();
                    tabLayout.setupWithViewPager(viewPager);
                    setupTabsIcons();
                }
        }
    }
}
