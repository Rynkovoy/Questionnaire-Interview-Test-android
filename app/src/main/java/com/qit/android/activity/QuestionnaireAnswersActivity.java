package com.qit.android.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.adapters.QuestionAdapter;
import com.qit.android.adapters.QuizTabsPagerAdapter;
import com.qit.android.models.answer.Answer;
import com.qit.android.models.event.Event;
import com.qit.android.models.question.Question;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.models.user.User;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.rest.api.QuestionApi;
import com.qit.android.rest.utils.QitApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnaireAnswersActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private QuestionAdapter questionAdapter;
    private RecyclerView rvQuestion;
    public Questionnaire questionnaire;

    private TextView saveAnswer;
    private TextView cancelActivity;

    public static Answer answer = new Answer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_answers);
        initToolbar();
        questionnaire = (Questionnaire) getIntent().getSerializableExtra("Questionnaire");
        rvQuestion = findViewById(R.id.rvQuestion);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvQuestion.setLayoutManager(mLayoutManager);

        TextView headerQuestion = (TextView) findViewById(R.id.tvQuestion);
        TextView mainTextQuestion= (TextView) findViewById(R.id.tvIsNecessary);
        TextView authorOfQuestion = (TextView) findViewById(R.id.tvAuthor);

        headerQuestion.setText(questionnaire.getSummary());
        mainTextQuestion.setText(questionnaire.getDescription());
        authorOfQuestion.setText(questionnaire.getAuthor().getFirstName()+" "+questionnaire.getAuthor().getLastName());

        saveAnswer = findViewById(R.id.answer_save_btn);
        saveAnswer.setOnClickListener(this);
        cancelActivity = findViewById(R.id.answer_cancel_btn);
        cancelActivity.setOnClickListener(this);

        showQuestions();
    }

    private void showQuestions() {
        List<Question> questionList = getQuestions();
        answer = new Answer();
        questionAdapter = new QuestionAdapter(questionList);
        rvQuestion.setAdapter(questionAdapter);
    }

    private List<Question> getQuestions() {
        final List<Question> questionList = questionnaire.getQuestionList();

        return questionList;
    }


    private void initToolbar() {
        toolbar = findViewById(R.id.toolbarQuestionnairesAnswersT);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_create_questionnaire));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.answer_save_btn) : {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();

                final DatabaseReference myRef = database.getReference("event");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            if (childDataSnapshot.getKey().equalsIgnoreCase(FirebaseEventinfoGodObj.getFirebaseCurrentEventName())) {

                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                assert firebaseUser != null;

                                final DatabaseReference myRefTemp = database.getReference("event/"+FirebaseEventinfoGodObj.getFirebaseCurrentEventName()+"/questionLists/"+FirebaseEventinfoGodObj.getFirebaseCurrentQuestion()+"/answerList/");

                                myRefTemp.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        List<Answer> answerList = new ArrayList<>();
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                            Answer answer = dataSnapshot1.getValue(Answer.class);
                                            answerList.add(answer);
                                        }

                                        answer.setAnswerCreatedByUser(firebaseUser.getUid());
                                        answerList.add(answer);
                                        myRefTemp.setValue(answerList);
                                        onBackPressed();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(QuestionnaireAnswersActivity.this, "THERE ARE SOME TROUBLES IN THE SKY", Toast.LENGTH_SHORT).show();
                        //onBackPressed();
                    }
                });
                break;
            }
            case (R.id.answer_cancel_btn): {
                onBackPressed();
                break;
            }
            default: {
                break;
            }
        }
    }
}
