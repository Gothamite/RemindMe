package com.gothamite.remindme;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gothamite.remindme.adapter.TabsFragmentAdapter;
import com.gothamite.remindme.dto.RemindDTO;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;
    private Toolbar toolBar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private TabsFragmentAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        initToolbar();
        initNavigationView();
        initTabs();


    }

    private void initToolbar() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        toolBar.setTitle(R.string.app_name);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolBar.inflateMenu(R.menu.menu);
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new TabsFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        new RemindMeTask().execute();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.actionNotificationItem:
                        showNotificationTab();
                }
                return true;
            }
        });
    }

    private void showNotificationTab() {
        viewPager.setCurrentItem(Constants.TAB_TWO);
    }

    private class RemindMeTask extends AsyncTask<Void, Void, RemindDTO>{

        @Override
        protected RemindDTO doInBackground(Void... params) {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return template.getForObject(Constants.URL.GET_REMIND_ITEM, RemindDTO.class);
        }

        @Override
        protected void onPostExecute(RemindDTO remindDTO) {
            List<RemindDTO> list = new ArrayList<>();
            list.add(remindDTO);
            adapter.setData(list);

        }
    }
}