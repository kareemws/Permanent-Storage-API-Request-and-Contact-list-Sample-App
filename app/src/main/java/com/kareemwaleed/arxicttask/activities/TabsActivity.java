package com.kareemwaleed.arxicttask.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
    private final int CONTACTS_PERMISSION_REQUEST = 0;
    private SharedPreferences user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        user = getSharedPreferences("user", MODE_PRIVATE);
        initViewVars();
        setSupportActionBar(toolbar);
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        setupTabsIcons();
    }

    private void initViewVars(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void setupViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ContactsListFragment(), "Contact List");
        viewPagerAdapter.addFragment(new JsonListFragment(), "Json List");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupTabsIcons(){
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
        switch (item.getItemId()){
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
}
