package com.qit.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.adapters.InterviewChatAdapter;
import com.qit.android.adapters.QuestionAdapter;
import com.qit.android.models.answer.Answer;
import com.qit.android.models.answer.Comment;
import com.qit.android.models.question.Question;
import com.qit.android.models.quiz.Interview;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.utils.BtnClickAnimUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InterviewCustomChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    //private QuestionAdapter questionAdapter;
    private RecyclerView rvInterviews;
    public Interview interview;
    public int interviewPos;
    private EditText editTextInterview;

    private InterviewChatAdapter interviewChatAdapter;

    private TextView saveAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_custom_chat);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initToolbar();
        interviewPos = getIntent().getExtras().getInt("Interview");

        interview = (Interview) getIntent().getSerializableExtra("InterviewObj");
        rvInterviews = findViewById(R.id.rvInterv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvInterviews.setLayoutManager(mLayoutManager);

        editTextInterview = findViewById(R.id.editTextInterview);

        TextView headerChat = (TextView) findViewById(R.id.tvQuestionInt);
        TextView mainTextChat = (TextView) findViewById(R.id.tvIsNecessaryInt);

        headerChat.setText(interview.getSummary());
        mainTextChat.setText(interview.getDescription());

        saveAnswer = findViewById(R.id.interview_save_btn);
        saveAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (!editTextInterview.getText().toString().equalsIgnoreCase("")){
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("event/" + FirebaseEventinfoGodObj.getFirebaseCurrentEventName() + "/interviewsList/" + interviewPos);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Interview interview = dataSnapshot.getValue(Interview.class);
                        Comment comment = new Comment();
                        comment.setComment(editTextInterview.getText().toString());
                        comment.setCommentCreatorName(FirebaseEventinfoGodObj.getFirebaseUserFullName());
                        comment.setDateOfComment(new Date(System.currentTimeMillis()));
                        interview.getComments().add(comment);
                        myRef.setValue(interview);
                        editTextInterview.setText("");
                        showInterview(InterviewCustomChatActivity.this.interview);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("ERROR", databaseError.getDetails());
                    }
                });
            }
            }
        });

        getInterview();
    }

    private void showInterview(Interview interview) {
        List<Comment> commentListm = interview.getComments();
        interviewChatAdapter = new InterviewChatAdapter(commentListm);
        rvInterviews.setAdapter(interviewChatAdapter);
    }

    private void getInterview() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("event/" + FirebaseEventinfoGodObj.getFirebaseCurrentEventName() + "/interviewsList/" + interviewPos);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Interview interview = dataSnapshot.getValue(Interview.class);
                InterviewCustomChatActivity.this.interview = interview;
                showInterview(InterviewCustomChatActivity.this.interview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("ERROR", databaseError.getDetails());
            }
        });
    }


    private void initToolbar() {
        toolbar = findViewById(R.id.toolbarQuestionnairesAnswersI);
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
            getSupportActionBar().setTitle(getResources().getString(R.string.the_ch));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
