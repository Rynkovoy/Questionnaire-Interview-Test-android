package com.qit.android.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.qit.R;
import com.qit.android.activity.AuthorizationActivity;
import com.qit.android.activity.EventConfirmationOfUsersActivity;
import com.qit.android.activity.QitActivity;
import com.qit.android.activity.RegistrationActivity;
import com.qit.android.adapters.QuizTabsPagerAdapter;
import com.qit.android.constants.DrawerItemTags;
import com.qit.android.constants.SharedPreferencesTags;
import com.qit.android.fragments.QuestionnaireTabFragment;
import com.qit.android.models.quiz.Interview;
import com.qit.android.models.quiz.Quiz;
import com.qit.android.rest.utils.FirebaseCountObjInList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class QitDrawerBuilder implements Drawer.OnDrawerItemClickListener {

    public static DrawerBuilder drawerBuilder;
    private Activity activity;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;

    private SecondaryDrawerItem itemConfirmUsers;
    private SecondaryDrawerItem itemQuestionnaire;
    private SecondaryDrawerItem itemInterview;
    private SecondaryDrawerItem itemChat;
    private SecondaryDrawerItem editProfile;
    private SecondaryDrawerItem itemLogout;
    public Drawer drawer;
    private boolean flag;
    private boolean isMy;

    public QitDrawerBuilder() {
        drawerBuilder = new DrawerBuilder();
    }

    public QitDrawerBuilder(boolean flag, boolean isMy) {
        drawerBuilder = new DrawerBuilder();
        this.flag = flag;
        this.isMy = isMy;
    }

    public QitDrawerBuilder setActivity(Activity activity) {
        this.activity = activity;
        drawerBuilder.withActivity(activity);
        return this;
    }

    public QitDrawerBuilder setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        drawerBuilder.withToolbar(toolbar);
        return this;
    }

    public Drawer build(Activity activity) {
        setActivity(activity);
        if (isMy){
        itemConfirmUsers = new SecondaryDrawerItem().withTag(DrawerItemTags.QONFIRM_TAB).withIcon(activity.getResources().getDrawable(R.drawable.ico_confirm)).withName(R.string.Confirm);}
        itemQuestionnaire = new SecondaryDrawerItem().withTag(DrawerItemTags.QUEST_TAB).withIcon(activity.getResources().getDrawable(R.drawable.ico_questionnarie)).withName(R.string.Questionnaires);
        itemInterview = new SecondaryDrawerItem().withTag(DrawerItemTags.INTER_TAB).withIcon(activity.getResources().getDrawable(R.drawable.ico_interv)).withName(R.string.Interviews);
        itemChat = new SecondaryDrawerItem().withTag(DrawerItemTags.CHAT_TAG).withIcon(activity.getResources().getDrawable(R.drawable.ico_chat)).withName(R.string.Room_Chat);
        editProfile = new SecondaryDrawerItem().withTag(DrawerItemTags.PROFILE_TAG).withIcon(activity.getResources().getDrawable(R.drawable.ico_settings)).withName(R.string.edit_profile);
        if (!flag) {
            itemLogout = new SecondaryDrawerItem().withTag(DrawerItemTags.LOGOUT_TAG).withIcon(activity.getResources().getDrawable(R.drawable.ico_exit)).withName(R.string.logout);
        } else {
            itemLogout = new SecondaryDrawerItem().withTag(DrawerItemTags.EXIT_TAG).withIcon(activity.getResources().getDrawable(R.drawable.ico_exit)).withName(R.string.logout_sercher);
        }

        //FirebaseCountObjInList firebaseCountObjInList = new FirebaseCountObjInList(itemQuestionnaire, itemInterview);
        //firebaseCountObjInList.getIntOfElems();

        AccountHeader accountHeaderBuilder = new QitAccountHeaderBuilder().setActivity(activity).build();

        if (!flag && isMy) {
            drawer = drawerBuilder
                    .addDrawerItems(
                            itemConfirmUsers,
                            itemQuestionnaire,
                            itemInterview,
                            itemChat,
                            editProfile,
                            itemLogout
                    )
                    .withOnDrawerItemClickListener(this)
                    .withAccountHeader(accountHeaderBuilder)
                    .withActionBarDrawerToggle(true)
                    .build();
        } else if (!flag && !isMy){
            drawer = drawerBuilder
                    .addDrawerItems(
                            itemQuestionnaire,
                            itemInterview,
                            itemChat,
                            editProfile,
                            itemLogout
                    )
                    .withOnDrawerItemClickListener(this)
                    .withAccountHeader(accountHeaderBuilder)
                    .withActionBarDrawerToggle(true)
                    .build();
        }
        else {
            drawer = drawerBuilder
                    .addDrawerItems(
                            editProfile,
                            itemLogout
                    )
                    .withOnDrawerItemClickListener(this)
                    .withAccountHeader(accountHeaderBuilder)
                    .withActionBarDrawerToggle(true)
                    .build();
        }
        return drawer;
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        switch (String.valueOf(drawerItem.getTag())) {
            case DrawerItemTags.QONFIRM_TAB:
                Intent intentA = new Intent(activity.getApplicationContext(), EventConfirmationOfUsersActivity.class);
                activity.getApplicationContext().startActivity(intentA.setFlags(FLAG_ACTIVITY_NEW_TASK));
                activity.finish();
                break;
            case DrawerItemTags.QUEST_TAB:
                QitActivity.mViewPager.getViewPager().setCurrentItem(0);
                break;
            case DrawerItemTags.INTER_TAB:
                QitActivity.mViewPager.getViewPager().setCurrentItem(1);
                break;
            case DrawerItemTags.CHAT_TAG:
                QitActivity.mViewPager.getViewPager().setCurrentItem(2);
                break;
            case DrawerItemTags.PROFILE_TAG:
                Intent intentB = new Intent(activity.getApplicationContext(), RegistrationActivity.class);
                intentB.putExtra("isRegistrationCHangedFlag", true);
                activity.getApplicationContext().startActivity(intentB.setFlags(FLAG_ACTIVITY_NEW_TASK));
                activity.finish();
                //Toast.makeText(view.getContext(), "Events", Toast.LENGTH_SHORT).show();
                break;
            case DrawerItemTags.LOGOUT_TAG:
                logout();
                break;
            case DrawerItemTags.EXIT_TAG:
                exit();
                break;
        }
        return false;
    }


    private void logout() {
        if (activity != null) {
            sharedPreferences = activity.getSharedPreferences(AuthorizationActivity.class.getName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SharedPreferencesTags.IS_AUTHORIZE, false);
            editor.commit();
            activity.onBackPressed();
        }
    }

    private void exit() {
        if (activity != null) {
            PreferenceManager.getDefaultSharedPreferences(activity).edit().putString("LOGIN", String.valueOf("")).apply();
            PreferenceManager.getDefaultSharedPreferences(activity).edit().putString("PASSWORD", String.valueOf("")).apply();
            PreferenceManager.getDefaultSharedPreferences(activity).edit().putString("ISCHECKED", String.valueOf("")).apply();
            activity.startActivity(new Intent(activity, AuthorizationActivity.class));
        }
    }
}
