package com.qit.android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.qit.R;
import com.qit.android.adapters.QuizTabsPagerAdapter;
import com.qit.android.navigation.QitDrawerBuilder;


public class QitActivity extends MainActivity {

    private MaterialViewPager mViewPager;
    private FloatingActionButton mFab;
    private int mPreviousVisibleItem;
    private QuizTabsPagerAdapter quizTabsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qit);

        mViewPager = findViewById(R.id.materialViewPager);
        mFab = findViewById(R.id.fabAddQuiz);

        addToolbar();
        configureMaterialViewPager();
        configureFloatingActionButton();
        configureTopLogo();

    }


    private void addToolbar() {
        Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            new QitDrawerBuilder().setToolbar(toolbar).build(this);
        }
    }


    private void configureMaterialViewPager() {
        quizTabsPagerAdapter = new QuizTabsPagerAdapter(getSupportFragmentManager());

        mViewPager.getViewPager().setAdapter(quizTabsPagerAdapter);
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount()+1);
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*Toast.makeText(QitActivity.this,
                        quizTabsPagerAdapter.getItem(position).getClass().getSimpleName(),
                        Toast.LENGTH_SHORT)
                        .show();*/
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

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "https://i.pinimg.com/originals/0c/96/b1/0c96b19dc89ffdaa7ff737cfc04a095f.png");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "https://cdn.pixabay.com/photo/2016/03/28/00/37/flat-1284770_960_720.png");
//                    case 2:
//                        return HeaderDesign.fromColorResAndUrl(
//                                R.color.green,
//                                "http://progress.hu/wp-content/uploads/2008/09/mountains.jpg");
                }
                return null;
            }
        });
    }


    private void configureFloatingActionButton() {
        mFab.hide(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFab.show(true);
                mFab.setShowAnimation(AnimationUtils.loadAnimation(QitActivity.this, R.anim.show_from_bottom));
                mFab.setHideAnimation(AnimationUtils.loadAnimation(QitActivity.this, R.anim.hide_to_bottom));
            }
        }, 300);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mViewPager.getViewPager().getCurrentItem()) {
                    case 0:
                        startActivity(new Intent(QitActivity.this, QuestionnaireCreationActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(QitActivity.this, InterviewCreationActivity.class));
                        break;
                }
            }
        });
    }


    private void configureTopLogo() {
        final View logo = findViewById(R.id.logo_white);
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
}