package com.qit.android.adapters;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.qit.R;
import com.qit.android.fragments.InterviewTabFragment;
import com.qit.android.fragments.QuestionnaireTabFragment;
import com.qit.android.fragments.TestTabFragment;

import java.util.ArrayList;
import java.util.List;

public class QuizTabsPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> tabsTitle;

    public QuizTabsPagerAdapter(FragmentManager fm) {
        super(fm);
        this.tabsTitle = new ArrayList<>();
        this.tabsTitle.add(0, "Questionnaires");
        this.tabsTitle.add(1, "Interviews");
        this.tabsTitle.add(2, "Tests");
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                QuestionnaireTabFragment questionnaireTabFragment = new QuestionnaireTabFragment();
                return questionnaireTabFragment;
            case 1:
                InterviewTabFragment interviewTabFragment = new InterviewTabFragment();
                return interviewTabFragment;
            case 2:
                TestTabFragment testTabFragment = new TestTabFragment();
                return testTabFragment;
            default:
                return null;
        }
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
