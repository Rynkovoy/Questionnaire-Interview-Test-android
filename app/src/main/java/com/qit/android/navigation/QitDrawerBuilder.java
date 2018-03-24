package com.qit.android.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
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
import com.qit.android.constants.DrawerItemTags;
import com.qit.android.constants.SharedPreferencesTags;

public class QitDrawerBuilder implements Drawer.OnDrawerItemClickListener {

    private DrawerBuilder drawerBuilder;
    private Activity activity;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;


    private SecondaryDrawerItem itemQuestionnaire;
    private SecondaryDrawerItem itemInterview;
    private SecondaryDrawerItem itemEvent;
    private SecondaryDrawerItem itemLogout;

    public QitDrawerBuilder() {
        this.drawerBuilder = new DrawerBuilder();
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
        itemQuestionnaire = new SecondaryDrawerItem().withTag(DrawerItemTags.QUEST_TAB).withName(R.string.Questionnaires);
        itemInterview = new SecondaryDrawerItem().withTag(DrawerItemTags.INTER_TAB).withName(R.string.Interviews);
        itemEvent = new SecondaryDrawerItem().withTag(DrawerItemTags.EVENT_TAG).withName(R.string.Events);
        itemLogout = new SecondaryDrawerItem().withTag(DrawerItemTags.LOGOUT_TAG).withName(R.string.logout);

        itemQuestionnaire.withBadge("19").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700));

        AccountHeader accountHeaderBuilder = new QitAccountHeaderBuilder().setActivity(activity).build();

        return drawerBuilder
                .addDrawerItems(
                        itemQuestionnaire,
                        itemInterview,
                        itemEvent,
                        itemLogout
                )
                .withOnDrawerItemClickListener(this)
                .withAccountHeader(accountHeaderBuilder)
                .build();
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        switch (String.valueOf(drawerItem.getTag())) {
            case DrawerItemTags.QUEST_TAB:
                Toast.makeText(view.getContext(), "Questionnaires", Toast.LENGTH_SHORT).show();
                break;
            case DrawerItemTags.INTER_TAB:
                Toast.makeText(view.getContext(), "Interviews", Toast.LENGTH_SHORT).show();
                break;
            case DrawerItemTags.EVENT_TAG:
                Toast.makeText(view.getContext(), "Events", Toast.LENGTH_SHORT).show();
                break;
            case DrawerItemTags.LOGOUT_TAG:
                logout();
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
            Intent intent = new Intent(activity, AuthorizationActivity.class);
            activity.startActivity(intent);
        }
    }
}
