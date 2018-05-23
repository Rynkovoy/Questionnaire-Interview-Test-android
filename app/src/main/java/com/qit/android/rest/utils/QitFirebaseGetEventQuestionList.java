package com.qit.android.rest.utils;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.android.adapters.QuestionnaireAdapter;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class QitFirebaseGetEventQuestionList {

    public List<Questionnaire> getListQuestions(final QuestionnaireAdapter questionnaireAdapter, final List<Questionnaire> qList) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference("event");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                qList.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot.getKey().equalsIgnoreCase(FirebaseEventinfoGodObj.getFirebaseCurrentEventName())) {

                        for (DataSnapshot child : childDataSnapshot.getChildren()) {
                            if (child.getKey().equalsIgnoreCase("questionLists")) {
                                for (DataSnapshot childObj : child.getChildren()){
                                    Questionnaire q = childObj.getValue(Questionnaire.class);
                                    qList.add(q);
                                }
                                Collections.reverse(qList);
                                myRef.removeEventListener(this);

                                try {
                                    questionnaireAdapter.notifyDataSetChanged();
                                } catch (Exception e) {e.printStackTrace();}
                            }
                        }
                    }
                }
            }

            @Override
           public void onCancelled(DatabaseError databaseError) {

    }
});
        return qList;

    }

}
