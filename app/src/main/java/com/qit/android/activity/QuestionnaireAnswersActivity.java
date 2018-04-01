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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.adapters.QuestionAdapter;
import com.qit.android.adapters.QuizTabsPagerAdapter;
import com.qit.android.models.question.Question;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.rest.api.QuestionApi;
import com.qit.android.rest.utils.QitApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnaireAnswersActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private QuestionAdapter questionAdapter;
    private RecyclerView rvQuestion;
    private Questionnaire questionnaire;

    private LinearLayout cardViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_answers);
        initToolbar();
        questionnaire = (Questionnaire) getIntent().getSerializableExtra("Questionnaire");
        rvQuestion = findViewById(R.id.rvQuestion);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvQuestion.setLayoutManager(mLayoutManager);

        TextView text = (TextView) findViewById(R.id.tvQuestion);
        TextView text2= (TextView) findViewById(R.id.tvIsNecessary);
        TextView text3 = (TextView) findViewById(R.id.tvAuthor);

        text.setText(questionnaire.getSummary());
        text2.setText(questionnaire.getDescription());
        text3.setText(questionnaire.getAuthor().getFirstName()+" "+questionnaire.getAuthor().getLastName());

        showQuestions();
    }

    private void showQuestions() {
        List<Question> questionList = getQuestions();
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
}
