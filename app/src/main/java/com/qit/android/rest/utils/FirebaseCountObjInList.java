package com.qit.android.rest.utils;


import android.graphics.Color;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.qit.R;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

public class FirebaseCountObjInList {

    public final String interviews = "Interviews";
    public final String questionnaries = "Questionnaries";

    public int interviewCounter = 0;
    public int questionnariesCounter = 0;

    private SecondaryDrawerItem itemQuestionnaire;
    private SecondaryDrawerItem itemInterview;

    public FirebaseCountObjInList(SecondaryDrawerItem itemQuestionnair, SecondaryDrawerItem itemInterview) {
        this.itemQuestionnaire = itemQuestionnair;
        this.itemInterview = itemInterview;
    }

    public void getIntOfElems() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("event");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                interviewCounter = 0;
                questionnariesCounter = 0;
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot.getKey().equalsIgnoreCase(FirebaseEventinfoGodObj.getFirebaseCurrentEventName())) {
                        for (DataSnapshot child : childDataSnapshot.getChildren()) {
                            if (child.getKey().equalsIgnoreCase("interviewsList")) {
                                for (DataSnapshot childObj : child.getChildren()) {
                                    interviewCounter++;
                                }
                                itemInterview.withBadge(String.valueOf(interviewCounter)).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.colorGreen));
                            } else if (child.getKey().equalsIgnoreCase("questionLists")) {
                                for (DataSnapshot childObj : child.getChildren()) {
                                    questionnariesCounter++;
                                }
                                itemQuestionnaire.withBadge(String.valueOf(questionnariesCounter)).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.colorGreen));
                            }
                        }
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
