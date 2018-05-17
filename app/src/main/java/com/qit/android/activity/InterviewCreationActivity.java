package com.qit.android.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.models.event.Event;
import com.qit.android.models.quiz.Interview;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.models.user.User;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.utils.BtnClickAnimUtil;
import com.qit.android.utils.QitEditTextCreator;
import com.qit.android.utils.QitInputType;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InterviewCreationActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private AppCompatEditText etTitle;
    private AppCompatEditText etDescription;

    private Button btnNext;
    private Interview interview;
    private int position = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_creation);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        try{
            Intent intent = getIntent();
            position = intent.getIntExtra("Position", -1);
        } catch (Exception e){e.printStackTrace();}

        initToolbar();
        initViewComponents();
        initInterview();

        if (position != -1){
            getEventDataFromFB(position);
        }

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbarQuestionnaireCreation2);
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
            getSupportActionBar().setTitle(getResources().getString(R.string.title_create_interview));
        }
    }

    private void initInterview() {
        interview = new Interview();
    }


    private void initViewComponents() {

        etTitle = findViewById(R.id.interEtTitle);
        etDescription = findViewById(R.id.interEtDescription);

        btnNext = findViewById(R.id.btnNext);

    }


    public void handleBtnNext(final View view) {
        BtnClickAnimUtil btnClickAnimUtil = new BtnClickAnimUtil(view, this);
        interview.setSummary(String.valueOf(etTitle.getText()));
        interview.setDescription(String.valueOf(etDescription.getText()));

        // TODO NEED TO BE IN OTHER CLASS LIKE FIREBASE GET USER
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("event");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!interview.getSummary().equalsIgnoreCase("") || !interview.getDescription().equalsIgnoreCase("")) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        Event event = childDataSnapshot.getValue(Event.class);
                        if (childDataSnapshot.getKey().equalsIgnoreCase(FirebaseEventinfoGodObj.getFirebaseCurrentEventName())) {
                            if (event.getInterviewsList().size() == 0) {
                                event.getInterviewsList().add(interview);
                            } else {
                                if (position == -1) {
                                    event.getInterviewsList().add(interview);
                                } else {
                                    //event.getInterviewsList().remove(event.getInterviewsList().size()-position-1);
                                    event.getInterviewsList().set(event.getInterviewsList().size() - position - 1, interview);
                                }
                            }
                            DatabaseReference myRef = database.getReference("event" + "/" + FirebaseEventinfoGodObj.getFirebaseCurrentEventName());
                            myRef.setValue(event);

                            startActivity(new Intent(InterviewCreationActivity.this, QitActivity.class));
                            finish();
                        }
                    }
                } else {
                    Snackbar.make((View) view.getParent(), "Not all fields are filled!", Snackbar.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("LOG", databaseError.getMessage().toString());
                //Snackbar.make(view, "There are some trables with firebase, sry!", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MaterialBaseTheme_AlertDialog);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(InterviewCreationActivity.this, QitActivity.class));
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void getEventDataFromFB (final int position) {
        //interview.setSummary(String.valueOf(etTitle.getText()));
        //interview.setDescription(String.valueOf(etDescription.getText()));

        // TODO NEED TO BE IN OTHER CLASS LIKE FIREBASE GET USER
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("event");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Event event = childDataSnapshot.getValue(Event.class);
                    if (childDataSnapshot.getKey().equalsIgnoreCase(FirebaseEventinfoGodObj.getFirebaseCurrentEventName())){
                        if (event.getInterviewsList().size() != 0){
                            interview = event.getInterviewsList().get(event.getInterviewsList().size()-1-position);
                            etTitle.setText(interview.getSummary());
                            etDescription.setText(interview.getDescription());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("LOG", databaseError.getMessage().toString());
                //Snackbar.make(view, "There are some trables with firebase, sry!", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, QitActivity.class));
        this.finish();
    }

}
