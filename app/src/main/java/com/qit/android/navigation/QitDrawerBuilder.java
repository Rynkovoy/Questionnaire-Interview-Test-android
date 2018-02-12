package com.qit.android.navigation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.qit.R;

public class QitDrawerBuilder implements Drawer.OnDrawerItemClickListener {

    private DrawerBuilder drawerBuilder;
    private Activity activity;
    private Toolbar toolbar;

    private SecondaryDrawerItem item1;
    private SecondaryDrawerItem item2;
    private SecondaryDrawerItem item3;

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

    public Drawer build() {

        item1 = new SecondaryDrawerItem().withTag(DrawerItemTag.QUEST_TAB).withName(R.string.questionnaire);
        item2 = new SecondaryDrawerItem().withTag(DrawerItemTag.INTER_TAB).withName(R.string.interview);
        item3 = new SecondaryDrawerItem().withTag(DrawerItemTag.TEST_TAG).withName(R.string.test);

        item1.withBadge("19").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700));

        AccountHeader accountHeaderBuilder = new QitAccountHeaderBuilder().setActivity(activity).build();

        return drawerBuilder
                .addDrawerItems(
                        item1,
                        item2,
                        item3
                )
                .withOnDrawerItemClickListener(this)
                .withAccountHeader(accountHeaderBuilder)
                .build();
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        switch (String.valueOf(drawerItem.getTag())) {
            case DrawerItemTag.QUEST_TAB:
                Toast.makeText(view.getContext(), "Questionnaire", Toast.LENGTH_SHORT).show();
                break;
            case DrawerItemTag.INTER_TAB:
                Toast.makeText(view.getContext(), "Interview", Toast.LENGTH_SHORT).show();
                break;
            case DrawerItemTag.TEST_TAG:
                Toast.makeText(view.getContext(), "Test", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
