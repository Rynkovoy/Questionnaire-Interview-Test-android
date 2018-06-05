package com.qit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.adapters.QuestionAdapter;
import com.qit.android.adapters.UserConfirmationAdapter;
import com.qit.android.models.answer.Answer;
import com.qit.android.models.event.ConfirmedUser;
import com.qit.android.models.event.Event;
import com.qit.android.models.question.Question;
import com.qit.android.models.user.User;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

import java.util.ArrayList;
import java.util.List;

public class EventConfirmationOfUsersActivity extends AppCompatActivity {


    private List<ConfirmedUser> confirmedUserList = new ArrayList<>();
    private Context context;
    private Toolbar toolbar;
    private RecyclerView rvConfirmUsers;
    private UserConfirmationAdapter userConfirmationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_confirmation_of_users);

        context = this;
        rvConfirmUsers = findViewById(R.id.rvUsersConfirm);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initToolbar();

        loadData();
    }

    private void showQuestions() {

        userConfirmationAdapter = new UserConfirmationAdapter(confirmedUserList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvConfirmUsers.setLayoutManager(mLayoutManager);
        rvConfirmUsers.setAdapter(userConfirmationAdapter);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbarQuestionnairesAnswersU);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Users, who are wait for confirmation");
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, QitActivity.class));
        this.finish();
    }

    public void loadData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("event");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = new Event();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot.getKey().equalsIgnoreCase(FirebaseEventinfoGodObj.getFirebaseCurrentEventName())) {
                        event = childDataSnapshot.getValue(Event.class);
                    }
                }
                confirmedUserList = event.getConfirmedUserList();
                showQuestions();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("LOG", databaseError.getMessage().toString());
                //Snackbar.make(view, "There are some trables with firebase, sry!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
