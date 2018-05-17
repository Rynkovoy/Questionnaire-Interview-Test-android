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
import android.widget.ListView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.adapters.InterviewAdapter;
import com.qit.android.adapters.QuestionnaireAdapter;
import com.qit.android.models.answer.Comment;
import com.qit.android.models.quiz.Interview;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.rest.api.InterviewApi;
import com.qit.android.rest.dto.InterviewDTO;
import com.qit.android.rest.utils.QitApi;
import com.qit.android.rest.utils.QitFirebaseGetEventQuestionList;
import com.qit.android.rest.utils.QitFirebaseGetInterviewList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InterviewTabFragment extends Fragment {

    //private static final String ON_FAILURE_TOAST_MESSAGE = "Cannot load data";

    private NestedScrollView mScrollView;
    private InterviewAdapter interviewAdapter;
    private View view;
    private RecyclerView recyclerView;
    private static List<Interview> interviews = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_interview_tab, container, false);
        //mScrollView = view.findViewById(R.id.scrollViewInterview);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("event/"+ FirebaseEventinfoGodObj.getFirebaseCurrentEventName()+"/interviewsList");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                interviews = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    Interview interview = child.getValue(Interview.class);
                    interviews.add(interview);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("ERROR", databaseError.getDetails());
            }
        });


        QitFirebaseGetInterviewList qitFirebaseGetInterviewList = new QitFirebaseGetInterviewList();
        interviewAdapter = new InterviewAdapter(interviews);
        qitFirebaseGetInterviewList.getInterviewList(interviewAdapter, interviews);

        //interviewAdapter = new InterviewAdapter(initInterviewList());
        recyclerView = view.findViewById(R.id.interviewRV);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(interviewAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);
    }

}
