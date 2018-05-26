package com.qit.android.rest.utils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.android.adapters.InterviewAdapter;
import com.qit.android.adapters.QuestionnaireAdapter;
import com.qit.android.models.quiz.Interview;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.models.user.User;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

import java.util.Collections;
import java.util.List;

public class QitFirebaseGetInterviewList {

    public List<Interview> getInterviewList(final InterviewAdapter interviewAdapter, final List<Interview> interviews) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("event");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                interviews.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot.getKey().equalsIgnoreCase(FirebaseEventinfoGodObj.getFirebaseCurrentEventName())) {
                        for (DataSnapshot child : childDataSnapshot.getChildren()) {
                            if (child.getKey().equalsIgnoreCase("interviewsList")) {
                                for (DataSnapshot childObj : child.getChildren()){
                                    Interview i = childObj.getValue(Interview.class);
                                    interviews.add(i);
                                }
                                Collections.reverse(interviews);
                                interviewAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return interviews;

    }
}
