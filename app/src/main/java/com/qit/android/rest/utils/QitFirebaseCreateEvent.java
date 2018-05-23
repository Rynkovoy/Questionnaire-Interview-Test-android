package com.qit.android.rest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.android.activity.QitActivity;
import com.qit.android.models.event.Event;
import com.qit.android.models.user.User;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QitFirebaseCreateEvent {

    private FirebaseAuth mAuth;
    public String eventName;

    public QitFirebaseCreateEvent (FirebaseAuth mAuth){
        this.mAuth = mAuth;
    }

    public void createEventInFirebase (final Context context, final Event event){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_s");
        SimpleDateFormat sdfShort = new SimpleDateFormat("yyyy.MM.dd HH:mm:s");

        final String strDate = sdf.format(c.getTime());
        String strDateshort = sdfShort.format(c.getTime());
        event.setDate(strDateshort);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        DatabaseReference myRef = database.getReference("user");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot.getKey().equalsIgnoreCase(mAuth.getCurrentUser().getUid())){
                        User userObj = childDataSnapshot.getValue(User.class);
                        event.setEventOwnerName(userObj.getFirstName()+ " " + userObj.getLastName());
                        DatabaseReference myRef = database.getReference("event" + "/event_" + mAuth.getCurrentUser().getUid() + "_" + strDate);
                        eventName = "event_" + mAuth.getCurrentUser().getUid() + "_" + strDate;
                        myRef.setValue(event);
                        FirebaseEventinfoGodObj.setFirebaseCurrentEventName(eventName);
                        context.startActivity(new Intent(context, QitActivity.class));
                        Activity activity = (Activity) context;
                        activity.finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
