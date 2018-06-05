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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.adapters.QuizTabsPagerAdapter;
import com.qit.android.models.event.Event;
import com.qit.android.navigation.QitDrawerBuilder;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

import pl.droidsonroids.gif.GifImageView;


public class QitActivity extends MainActivity {

    public static MaterialViewPager mViewPager;
    public static FloatingActionButton mFab;
    private int mPreviousVisibleItem;
    private QuizTabsPagerAdapter quizTabsPagerAdapter;
    private ImageView fabPlusImg;

    private static Drawable imgOne;
    private static Drawable imgTwo;
    private static Drawable imgThree;

    public static boolean isShowFab = true;


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
        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {

            final FirebaseAuth mAuth = FirebaseAuth.getInstance();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("event/"+ FirebaseEventinfoGodObj.getFirebaseCurrentEventName());
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Event event = dataSnapshot.getValue(Event.class);
                    if (event.getEventOwner().equalsIgnoreCase(mAuth.getUid()) && event.isNewUserInEventeNeedToBeConfirmed()){
                        new QitDrawerBuilder(false, true).setToolbar(toolbar).build(QitActivity.this);
                    } else {
                        new QitDrawerBuilder().setToolbar(toolbar).build(QitActivity.this);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    new QitDrawerBuilder(false, true).setToolbar(toolbar).build(QitActivity.this);
                }
            });
        }
    }


    public static String aa;
    public static String bb;
    public static String cc;
    private void configureMaterialViewPager() {
        aa = getString(R.string.que);
        bb = getString(R.string.the_ch);
        cc = getString(R.string.roo_ch);

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
                        if (isShowFab) {
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