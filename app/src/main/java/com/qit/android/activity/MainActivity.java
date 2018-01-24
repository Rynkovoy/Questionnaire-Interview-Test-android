package com.qit.android.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.mikepenz.materialdrawer.Drawer;
import com.qit.R;
import com.qit.android.adapters.QuizTabsPagerAdapter;
import com.qit.android.navigation.QitDrawerBuilder;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.qitToolbar);
        setSupportActionBar(toolbar);
        addNavigationDrawer(toolbar);



        final ViewPager viewPager = findViewById(R.id.tabsViewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        List<String> tabsTitle = new ArrayList<>();
        tabsTitle.add(getResources().getString(R.string.questionnaire));
        tabsTitle.add(getResources().getString(R.string.interview));
        tabsTitle.add(getResources().getString(R.string.test));


        QuizTabsPagerAdapter pagerAdapter = new QuizTabsPagerAdapter(getSupportFragmentManager(), tabsTitle);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void addNavigationDrawer(Toolbar toolbar) {
        Drawer drawer = new QitDrawerBuilder().setActivity(this).setToolbar(toolbar).build();
    }


}