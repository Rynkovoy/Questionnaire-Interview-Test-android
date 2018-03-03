package com.qit.android.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qit.R;
import com.qit.android.models.Question;
import com.qit.android.models.Questionnaire;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionsCreationActivity extends AppCompatActivity {

    private LinearLayout layoutScrolledQuestions;
    private List<CardView> questionLayoutList;
    private Toolbar toolbar;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_creation);
        initToolbar();

        layoutScrolledQuestions = findViewById(R.id.layoutScrolledQuestions);
        questionLayoutList = new ArrayList();
        radioGroup = new RadioGroup(QuestionsCreationActivity.this);
        LinearLayout.LayoutParams radioLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioLayoutParams.setMargins(0,22,0,0 );
        radioGroup.setLayoutParams(radioLayoutParams);

        Questionnaire questionnaire = (Questionnaire) getIntent().getSerializableExtra("Questionnaire");

    }


    public void createQuestion(View view) {
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 15, 15, 0);
        final CardView cardView = new CardView(this);
        cardView.setLayoutParams(layoutParams);

        final LinearLayout linearLayoutMain = new LinearLayout(this);
        linearLayoutMain.setOrientation(LinearLayout.VERTICAL);
        linearLayoutMain.setLayoutParams(layoutParams);

        cardView.addView(linearLayoutMain);

        final LinearLayout linearLayout1 =  new LinearLayout(this);
        linearLayout1.setLayoutParams(layoutParams);

        EditText etQuestion = new EditText(this);
        etQuestion.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        etQuestion.setSingleLine(false);
        etQuestion.setTextColor(getResources().getColor(R.color.colorAuthText));
        etQuestion.setHint("Question");
        etQuestion.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3.5f));

        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter(this, R.layout.custom_textview_to_spinner, getResources().getStringArray(R.array.question_type));

        LinearLayout.LayoutParams spinnerLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 6.5f);
        spinnerLayoutParams.setMargins(12, 0, 0, 0);
        Spinner spinnerQuestionType = new Spinner(this);
        spinnerQuestionType.setAdapter(spinnerAdapter);
        spinnerQuestionType.setLayoutParams(spinnerLayoutParams);

        linearLayout1.setWeightSum(10f);
        linearLayout1.addView(etQuestion, 0);
        linearLayout1.addView(spinnerQuestionType, 1);


        spinnerQuestionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Toast.makeText(QuestionsCreationActivity.this, "One of the list", Toast.LENGTH_SHORT).show();

                        AppCompatRadioButton radioButton = new AppCompatRadioButton(QuestionsCreationActivity.this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(QuestionsCreationActivity.this, R.color.green)));
                        }

                        
                        RadioGroup radioGroup = new RadioGroup(QuestionsCreationActivity.this);
                        LinearLayout.LayoutParams radioLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        radioLayoutParams.setMargins(0,22,0,0 );
                        radioGroup.setLayoutParams(radioLayoutParams);
                        radioGroup.addView(radioButton);


                        EditText editText = new EditText(QuestionsCreationActivity.this);
                        editText.setTextColor(getResources().getColor(R.color.colorAuthText));
                        editText.setHint("Question");
                        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.5f));

                        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        imageLayoutParams.setMargins(0,22,0,0 );
                        ImageView btnDelete = new ImageView(QuestionsCreationActivity.this);
                        btnDelete.setLayoutParams(imageLayoutParams);
                        btnDelete.setImageResource(R.drawable.ic_remove_circle_outline_black_18dp);
                        btnDelete.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 8.5f));

                        LinearLayout linearLayoutAnswer = new LinearLayout(QuestionsCreationActivity.this);
                        linearLayoutAnswer.setOrientation(LinearLayout.HORIZONTAL);


                        linearLayoutAnswer.addView(radioGroup);
                        linearLayoutAnswer.addView(editText);
                        linearLayoutAnswer.addView(btnDelete);


                        linearLayoutMain.addView(linearLayoutAnswer);

                        break;
                    case 1:
                        Toast.makeText(QuestionsCreationActivity.this, "A few from the list", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(QuestionsCreationActivity.this, "Text (detailed)", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        questionLayoutList.add(cardView);

        linearLayoutMain.addView(linearLayout1);
        layoutScrolledQuestions.addView(cardView);

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.questionToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_create_questionnaire));
        }
    }
}
