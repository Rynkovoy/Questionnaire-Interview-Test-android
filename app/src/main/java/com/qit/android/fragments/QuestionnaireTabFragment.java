package com.qit.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnaireTabFragment extends Fragment {

    private static final String ON_FAILURE_TOAST_MESSAGE = "Cannot load data";

    private NestedScrollView mScrollView;
    private QuestionnaireAdapter questionnaireAdapter;
    private View view;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionnaire_tab, container, false);
        mScrollView = view.findViewById(R.id.scrollViewQuestionnaire);

        questionnaireAdapter = new QuestionnaireAdapter(initQuestionnaireList());
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

    private List<Questionnaire> initQuestionnaireList() {
        final List<Questionnaire> questionnaires = new ArrayList<>();

        QitApi.getApi(QuestionnaireApi.class).findAllQuestionnaires().enqueue(new Callback<List<Questionnaire>>() {
            @Override
            public void onResponse(Call<List<Questionnaire>> call, Response<List<Questionnaire>> response) {
                questionnaires.addAll(response.body());
                questionnaireAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Questionnaire>> call, Throwable t) {
                Snackbar.make(view, ON_FAILURE_TOAST_MESSAGE, Snackbar.LENGTH_LONG).show();
            }
        });

        return questionnaires;
    }

    public void refreshRecyclerView() {
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }
}
