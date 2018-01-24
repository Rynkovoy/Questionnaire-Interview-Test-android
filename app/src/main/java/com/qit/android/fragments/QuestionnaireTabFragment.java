package com.qit.android.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qit.R;
import com.qit.android.adapters.QuizAdapter;
import com.qit.android.rest.api.QuestionnaireApi;
import com.qit.android.rest.dto.QuestionnaireDTO;
import com.qit.android.rest.utils.QitApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnaireTabFragment extends Fragment {

    private static final String ON_FAILURE_TOAST_MESSAGE = "Cannot load data";

    private QuizAdapter questionnaireAdapter;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionnaire_tab, container, false);
        ListView listView = view.findViewById(R.id.listViewQuestionnaires);
        questionnaireAdapter = new QuizAdapter(view.getContext(), initQuestionnaireList(listView));

        /*swipeRefreshLayout = view.findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });*/
        return view;
    }

   /* private void refreshContent() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                questionnaireAdapter = new QuizAdapter(view.getContext(), initQuestionnaireList());
                listView.setAdapter(questionnaireAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }*/


    private List<QuestionnaireDTO> initQuestionnaireList(final ListView listView) {
        final List<QuestionnaireDTO> questionnaireDTOs = new ArrayList<>();
        QitApi.getApi(QuestionnaireApi.class).findAllQuestionnaires().enqueue(new Callback<List<QuestionnaireDTO>>() {
            @Override
            public void onResponse(Call<List<QuestionnaireDTO>> call, Response<List<QuestionnaireDTO>> response) {
                questionnaireDTOs.addAll(response.body());
                listView.setAdapter(questionnaireAdapter);
            }

            @Override
            public void onFailure(Call<List<QuestionnaireDTO>> call, Throwable t) {
//                Snackbar.make(view, ON_FAILURE_TOAST_MESSAGE, Snackbar.LENGTH_LONG);
            }
        });

        return questionnaireDTOs;
    }
}
