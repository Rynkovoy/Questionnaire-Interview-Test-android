package com.qit.android.navigation;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.qit.R;

public class QitAccountHeaderBuilder {

    private AccountHeaderBuilder accountHeaderBuilder;
    private Activity activity;
    private Toolbar toolbar;

    public QitAccountHeaderBuilder() {
        this.accountHeaderBuilder = new AccountHeaderBuilder();
    }

    public QitAccountHeaderBuilder setActivity(Activity activity) {
        this.activity = activity;
        accountHeaderBuilder.withActivity(activity);
        return this;
    }

    public AccountHeader build() {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.color.material_drawer_primary_light)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(R.drawable.person)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        return headerResult;
    }
}
