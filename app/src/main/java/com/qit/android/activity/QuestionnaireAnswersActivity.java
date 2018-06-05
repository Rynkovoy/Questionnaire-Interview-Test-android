package com.qit.android.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.qit.android.models.answer.Variant;
import com.qit.android.models.event.Event;
import com.qit.android.models.question.Question;
import com.qit.android.models.question.QuestionType;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.models.user.User;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.rest.api.QuestionApi;
import com.qit.android.rest.utils.QitApi;
import com.qit.android.utils.BtnClickAnimUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnaireAnswersActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private QuestionAdapter questionAdapter;
    private RecyclerView rvQuestion;
    public Questionnaire questionnaire;
    public List<Question> questionList;
    private TextView saveAnswer;

    //public static List<Answer> answers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_answers);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initToolbar();
        questionnaire = (Questionnaire) getIntent().getSerializableExtra("Questionnaire");
        rvQuestion = findViewById(R.id.rvQuestion);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvQuestion.setLayoutManager(mLayoutManager);

        TextView headerQuestion = findViewById(R.id.tvQuestion);
        TextView mainTextQuestion = findViewById(R.id.tvIsNecessary);
        TextView authorOfQuestion = findViewById(R.id.tvAuthor);

        headerQuestion.setText(questionnaire.getSummary());
        mainTextQuestion.setText(questionnaire.getDescription());
        authorOfQuestion.setText(questionnaire.getAuthor().getFirstName() + " " + questionnaire.getAuthor().getLastName());

        saveAnswer = findViewById(R.id.answer_save_btn);
        saveAnswer.setOnClickListener(this);

        showQuestions();
    }

    private void showQuestions() {
        List<Question> questionList = getQuestions();
        questionAdapter = new QuestionAdapter(questionList);
        rvQuestion.setAdapter(questionAdapter);
    }

    private List<Question> getQuestions() {
        questionList = questionnaire.getQuestionList();

        return questionList;
    }


    private void initToolbar() {
        toolbar = findViewById(R.id.toolbarQuestionnairesAnswersT);
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
    public void onClick(final View view) {
        BtnClickAnimUtil btnClickAnimUtil = new BtnClickAnimUtil(view, this);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();


        switch (view.getId()) {
            case (R.id.answer_save_btn): {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                for (int x = 0; x < questionList.size(); x++) {
                    final DatabaseReference myRefTemp = database.getReference("event/" + FirebaseEventinfoGodObj.getFirebaseCurrentEventName() + "/questionLists/" + FirebaseEventinfoGodObj.getFirebaseCurrentQuestion() + "/questionList/" + x);
                    final int finalX = x;
                    myRefTemp.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Question question = dataSnapshot.getValue(Question.class);
                            boolean flagIsAlreadyAnswer = false;

                            for (int i = 0; i < question.getVariants().size(); i++) {
                                for (int y = 0; y < question.getVariants().get(i).getAnswers().size(); y++) {
                                    if (question.getVariants().get(i).getAnswers().get(y).getAnswerCreatedByUser().equalsIgnoreCase(mAuth.getUid())) {
                                        flagIsAlreadyAnswer = true;
                                    }
                                }
                            }

                            if (flagIsAlreadyAnswer) {
                                Snackbar.make(view, "You are already answered", 0).show();
                            } else {
                                question = questionList.get(finalX);
                                    for (int i = 0; i < question.getVariants().size(); i++) {
                                        for (int y = 0; y < question.getVariants().get(i).getAnswers().size();y++){
                                            question.getVariants().get(i).getAnswers().get(y).setAnswerCreatedByUser(mAuth.getUid());
                                        }
                                    }
                                myRefTemp.setValue(question);


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            onBackPressed();
                        }
                    });
                    onBackPressed();
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, QitActivity.class));
        this.finish();
    }
}
