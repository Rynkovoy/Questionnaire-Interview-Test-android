package com.qit.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.qit.R;
import com.qit.android.adapters.QuestionnaireAdapter;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.rest.api.QuestionnaireApi;
import com.qit.android.rest.api.QuestionnaireApi2;
import com.qit.android.rest.dto.QuestionnaireDTO;
import com.qit.android.rest.utils.QitApi;
import com.qit.android.rest.utils.QitFirebaseGetEventQuestionList;
import com.qit.android.rest.utils.QitFirebaseGettingEventList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnaireTabFragment extends Fragment {

    //private static final String ON_FAILURE_TOAST_MESSAGE = "Cannot load data";

    private NestedScrollView mScrollView;
    private QuestionnaireAdapter questionnaireAdapter;
    private View view;
    private RecyclerView recyclerView;
    private static List<Questionnaire> questionnaires = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionnaire_tab, container, false);
        //mScrollView = view.findViewById(R.id.scrollViewQuestionnaire);
        //final List<Questionnaire> questionnaires = new ArrayList<>();

        QitFirebaseGetEventQuestionList qitFirebaseGetEventQuestionList = new QitFirebaseGetEventQuestionList();



        questionnaireAdapter = new QuestionnaireAdapter(questionnaires);
        qitFirebaseGetEventQuestionList.getListQuestions(questionnaireAdapter, questionnaires);

        recyclerView = view.findViewById(R.id.questionnaireRV);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(questionnaireAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);
    }


    public void refreshRecyclerView() {
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();

        try {
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            ft.remove(this).commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
