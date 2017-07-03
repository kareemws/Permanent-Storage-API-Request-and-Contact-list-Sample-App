package com.kareemwaleed.arxicttask.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kareemwaleed.arxicttask.R;
import com.kareemwaleed.arxicttask.fragments.ContactsListFragment;
import com.kareemwaleed.arxicttask.fragments.JsonListFragment;
import com.kareemwaleed.arxicttask.support.ViewPagerAdapter;

public class TabsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private final int CONTACTS_PERMISSION_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        initViewVars();
        setSupportActionBar(toolbar);
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        setupTabsIcons();
//        int isPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
//        if (isPermissionGranted == PackageManager.PERMISSION_GRANTED) {
//        } else {
//            Log.i("Kareem:", "here");
//            ActivityCompat.requestPermissions(this
//                    , new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_PERMISSION_REQUEST);
//        }
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

//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.i("Kareem:", "here");
//        switch (requestCode) {
//            case CONTACTS_PERMISSION_REQUEST:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.i("Kareem:", "Granted");
//                } else {
//                    Log.i("Kareem:", "Denied");
//                }
//        }
//    }
}
