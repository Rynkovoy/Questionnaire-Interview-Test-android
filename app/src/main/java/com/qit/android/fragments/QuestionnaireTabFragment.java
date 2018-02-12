package com.qit.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.qit.R;
import com.qit.android.adapters.QuestionnaireAdapter;
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

    private NestedScrollView mScrollView;
    private QuestionnaireAdapter questionnaireAdapter;
    private View view;
    private ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionnaire_tab, container, false);
        mScrollView = view.findViewById(R.id.scrollView);
        questionnaireAdapter = new QuestionnaireAdapter(view.getContext(), initQuestionnaireList());
        listView = view.findViewById(R.id.questionnaireListView);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);
    }

    private List<QuestionnaireDTO> initQuestionnaireList() {
        final List<QuestionnaireDTO> questionnaireDTOs = new ArrayList<>();
        QitApi.getApi(QuestionnaireApi.class).findAllQuestionnaires().enqueue(new Callback<List<QuestionnaireDTO>>() {
            @Override
            public void onResponse(Call<List<QuestionnaireDTO>> call, Response<List<QuestionnaireDTO>> response) {
                questionnaireDTOs.addAll(response.body());
                listView.setAdapter(questionnaireAdapter);
            }

            @Override
            public void onFailure(Call<List<QuestionnaireDTO>> call, Throwable t) {
                Snackbar.make(view, ON_FAILURE_TOAST_MESSAGE, Snackbar.LENGTH_LONG).show();
            }
        });

        return questionnaireDTOs;
    }
}
