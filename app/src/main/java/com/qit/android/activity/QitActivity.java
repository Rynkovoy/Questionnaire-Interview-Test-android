package com.qit.android.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.mikepenz.materialdrawer.Drawer;
import com.qit.R;
import com.qit.android.adapters.QuizTabsPagerAdapter;
import com.qit.android.navigation.QitDrawerBuilder;


public class QitActivity extends MainActivity {

    private MaterialViewPager mViewPager;
    private FloatingActionButton mFab;
    private int mPreviousVisibleItem;
    private QuizTabsPagerAdapter quizTabsPagerAdapter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addMaterialViewPager();
        addToolbar();
        addFloatingActionButton();
    }


    private void addMaterialViewPager() {
        mViewPager = findViewById(R.id.materialViewPager);

        quizTabsPagerAdapter = new QuizTabsPagerAdapter(getSupportFragmentManager());
        mViewPager.getViewPager().setAdapter(quizTabsPagerAdapter);
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "http://phandroid.s3.amazonaws.com/wp-content/uploads/2014/06/android_google_moutain_google_now_1920x1080_wallpaper_Wallpaper-HD_2560x1600_www.paperhi.com_-640x400.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://www.hdiphonewallpapers.us/phone-wallpapers/540x960-1/540x960-mobile-wallpapers-hd-2218x5ox3.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                }
                return null;
            }
        });

        View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void addToolbar() {
        Toolbar toolbar = mViewPager.getToolbar();
        if (mViewPager != null && toolbar != null) {
            new QitDrawerBuilder()
                    .setToolbar(toolbar)
                    .build(this);
        }
    }


    private void addFloatingActionButton() {
        mFab = findViewById(R.id.fabAddQuiz);

        mFab.hide(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFab.show(true);
                mFab.setShowAnimation(AnimationUtils.loadAnimation(QitActivity.this, R.anim.show_from_bottom));
                mFab.setHideAnimation(AnimationUtils.loadAnimation(QitActivity.this, R.anim.hide_to_bottom));
            }
        }, 300);

        mViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(QitActivity.this,
                        quizTabsPagerAdapter.getItem(position).getClass().getSimpleName(),
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state > mPreviousVisibleItem) {
                    mFab.hide(true);
                } else if (state < mPreviousVisibleItem) {
                    mFab.show(true);
                }
                mPreviousVisibleItem = state;
            }
        });
    }

}