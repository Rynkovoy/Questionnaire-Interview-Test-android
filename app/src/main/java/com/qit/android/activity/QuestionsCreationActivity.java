package com.qit.android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qit.R;
import com.qit.android.models.Question;
import com.qit.android.models.Questionnaire;

import java.io.Serializable;

public class QuestionsCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_creation);

        Questionnaire questionnaire = (Questionnaire) getIntent().getSerializableExtra("Questionnaire");
        System.out.println(questionnaire);
    }
}
