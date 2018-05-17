package com.qit.android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.qit.R;
import com.qit.android.adapters.QuizTabsPagerAdapter;
import com.qit.android.navigation.QitDrawerBuilder;

import pl.droidsonroids.gif.GifImageView;


public class QitActivity extends MainActivity {

    private MaterialViewPager mViewPager;
    private FloatingActionButton mFab;
    private int mPreviousVisibleItem;
    private QuizTabsPagerAdapter quizTabsPagerAdapter;
    private ImageView fabPlusImg;

    private static Drawable imgOne;
    private static Drawable imgTwo;
    private static Drawable imgThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qit);

        imgOne = getResources().getDrawable(R.drawable.top_1);
        imgTwo = getResources().getDrawable(R.drawable.top_2);
        imgThree = getResources().getDrawable(R.drawable.top_3);

        fabPlusImg = findViewById(R.id.fabPlusImg);

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
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getChildCount()+1);
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int pos = -1;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(QitActivity.this, position+"", Toast.LENGTH_SHORT).show();
                if(position == 2) {
                    pos = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (pos == 2) {
                    pos = -1;
                    mFab.hide(true);
                    fabPlusImg.setVisibility(View.GONE);
                } else {
                    if (state > mPreviousVisibleItem) {
                        mFab.hide(true);
                        fabPlusImg.setVisibility(View.GONE);
                    } else if (state < mPreviousVisibleItem) {
                        mFab.show(true);
                        fabPlusImg.setVisibility(View.VISIBLE);
                    }
                    mPreviousVisibleItem = state;
                }
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorAndDrawable(
                                getResources().getColor(R.color.blue),
                                imgOne);
                    case 1:
                        return HeaderDesign.fromColorAndDrawable(
                                getResources().getColor(R.color.cyan),
                                imgTwo);
                    case 2:

                        return HeaderDesign.fromColorAndDrawable(
                                getResources().getColor(R.color.green),
                                imgThree);
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
                        finish();
                        break;
                    case 1:
                        startActivity(new Intent(QitActivity.this, InterviewCreationActivity.class));
                        finish();
                        break;
                    case 2:
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
//
//                    try {
//                        final GifImageView givImageView = (GifImageView)findViewById(R.id.funnyGif);
//                        givImageView.setVisibility(View.VISIBLE);
//
//                        new Handler().postDelayed(
//                                new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        givImageView.setVisibility(View.GONE);
//                                    }
//                                }, 1500);
//                    }catch (Exception e) {e.printStackTrace();}
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
                startActivity(new Intent(QitActivity.this, NewEventOrChoseEventActivity.class));
                finish();
    }
}