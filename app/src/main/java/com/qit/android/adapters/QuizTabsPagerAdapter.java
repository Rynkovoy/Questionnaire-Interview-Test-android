package com.qit.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.qit.android.fragments.InterviewTabFragment;
import com.qit.android.fragments.QuestionnaireTabFragment;
import com.qit.android.fragments.ChatFragment;

import java.util.ArrayList;
import java.util.List;

public class QuizTabsPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> tabsTitle;
    private FragmentManager fragmentManager;

    public QuizTabsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.tabsTitle = new ArrayList<>();
        this.tabsTitle.add(0, "Questionnaires");
        this.tabsTitle.add(1, "Interviews");
        this.tabsTitle.add(2, "Room Chat");
    }

    @Override
    public Fragment getItem(int position) {
        Fragment selectedTabFragment;
        switch (position % 4) {
            case 0:
                selectedTabFragment = new QuestionnaireTabFragment();
                break;
            case 1:
                selectedTabFragment = new InterviewTabFragment();
                break;
            case 2:
                selectedTabFragment = new ChatFragment();
                break;
            default:
                return null;
        }

        //todo refresh list view on fragments when tab is changed
        /*fragmentManager.beginTransaction()
                .detach(selectedTabFragment)
                .attach(selectedTabFragment)
                .commit();*/

        return selectedTabFragment;
    }

    @Override
    public int getCount() {
        return tabsTitle.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabsTitle.get(position);
    }
}
