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
import com.qit.android.adapters.InterviewAdapter;
import com.qit.android.rest.api.InterviewApi;
import com.qit.android.rest.dto.InterviewDTO;
import com.qit.android.rest.utils.QitApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InterviewTabFragment extends Fragment {

    private static final String ON_FAILURE_TOAST_MESSAGE = "Cannot load data";

    private NestedScrollView mScrollView;
    private InterviewAdapter interviewAdapter;
    private View view;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionnaire_tab, container, false);
        mScrollView = view.findViewById(R.id.scrollView);
        interviewAdapter = new InterviewAdapter(view.getContext(), initInterviewList());
        listView = view.findViewById(R.id.questionnaireListView);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);
    }



    private List<InterviewDTO> initInterviewList() {
        final List<InterviewDTO> interviewDTOs = new ArrayList<>();
        QitApi.getApi(InterviewApi.class).findAllInterviews().enqueue(new Callback<List<InterviewDTO>>() {
            @Override
            public void onResponse(Call<List<InterviewDTO>> call, Response<List<InterviewDTO>> response) {
                interviewDTOs.addAll(response.body());
                listView.setAdapter(interviewAdapter);
            }

            @Override
            public void onFailure(Call<List<InterviewDTO>> call, Throwable t) {
                Snackbar.make(view, ON_FAILURE_TOAST_MESSAGE, Snackbar.LENGTH_LONG);
            }
        });

        return interviewDTOs;
    }


}
