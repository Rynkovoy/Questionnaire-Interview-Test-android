package com.qit.android.activity;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_answers);
        initToolbar();
        questionnaire = (Questionnaire) getIntent().getSerializableExtra("Questionnaire");
        rvQuestion = findViewById(R.id.rvQuestion);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvQuestion.setLayoutManager(mLayoutManager);

        showQuestions();
    }

    private void showQuestions() {
        List<Question> questionList = getQuestions();
        questionAdapter = new QuestionAdapter(questionList);
        rvQuestion.setAdapter(questionAdapter);
    }

    private List<Question> getQuestions() {
        final List<Question> questionList = new ArrayList<>();
        QitApi.getApi(QuestionApi.class).findQuestionByQuestionnaireId(questionnaire.getId())
                .enqueue(new Callback<List<Question>>() {
                    @Override
                    public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                        questionList.addAll(response.body());
                        questionAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Question>> call, Throwable t) {

                    }
                });

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
    }
}
