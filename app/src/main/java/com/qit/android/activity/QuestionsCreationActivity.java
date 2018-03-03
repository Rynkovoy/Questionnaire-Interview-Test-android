package com.qit.android.activity;

import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.qit.R;
import com.qit.android.models.Questionnaire;

import java.util.ArrayList;
import java.util.List;

public class QuestionsCreationActivity extends AppCompatActivity {

    private LinearLayout layoutScrolledQuestions;
    private List<CardView> questionLayoutList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_creation);
        initToolbar();

        layoutScrolledQuestions = findViewById(R.id.layoutScrolledQuestions);
        questionLayoutList = new ArrayList();
        LinearLayout.LayoutParams radioLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioLayoutParams.setMargins(0,22,0,0 );

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
        etQuestion.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 4.0f));

        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter(this, R.layout.custom_textview_to_spinner, getResources().getStringArray(R.array.question_type));

        LinearLayout.LayoutParams spinnerLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 6.0f);
        spinnerLayoutParams.setMargins(12, 0, 0, 0);
        Spinner spinnerQuestionType = new Spinner(this);
        spinnerQuestionType.setAdapter(spinnerAdapter);
        spinnerQuestionType.setLayoutParams(spinnerLayoutParams);

        linearLayout1.setWeightSum(10f);
        linearLayout1.addView(etQuestion, 0);
        linearLayout1.addView(spinnerQuestionType, 1);

        final List<RadioButton> radioButtons = new ArrayList<>();

        spinnerQuestionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int childCount = linearLayoutMain.getChildCount();
                int childIndex = linearLayoutMain.indexOfChild(linearLayout1);
                switch (i) {
                    case 0:
                        for (int j = childCount - 1; j > childIndex; j--) {
                            linearLayoutMain.removeViewAt(j);
                        }

                        linearLayoutMain.addView(addRadioQuestion(radioButtons));

                        final Button btnAdd = new Button(QuestionsCreationActivity.this);
                        btnAdd.setText("Add");

                        linearLayoutMain.addView(btnAdd);

                        btnAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int childIndex = linearLayoutMain.indexOfChild(btnAdd);
                                linearLayoutMain.removeViewAt(childIndex);
                                linearLayoutMain.addView(addRadioQuestion(radioButtons));
                                linearLayoutMain.addView(btnAdd);
                            }
                        });
                        break;
                    case 1:
                        for (int j = childCount - 1; j > childIndex; j--) {
                            linearLayoutMain.removeViewAt(j);
                        }

                        linearLayoutMain.addView(addCheckboxQuestion());

                        final Button btnAdd2 = new Button(QuestionsCreationActivity.this);
                        btnAdd2.setText("Add");

                        linearLayoutMain.addView(btnAdd2);

                        btnAdd2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int childIndex = linearLayoutMain.indexOfChild(btnAdd2);
                                linearLayoutMain.removeViewAt(childIndex);
                                linearLayoutMain.addView(addCheckboxQuestion());
                                linearLayoutMain.addView(btnAdd2);
                            }
                        });
                        break;
                    case 2:
                        for (int j = childCount - 1; j > childIndex; j--) {
                            linearLayoutMain.removeViewAt(j);
                        }
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

    private LinearLayout addRadioQuestion(final List<RadioButton> radioButtons) {
        Toast.makeText(QuestionsCreationActivity.this, "One of the list", Toast.LENGTH_SHORT).show();

        LinearLayout linearLayoutAnswer = new LinearLayout(QuestionsCreationActivity.this);
        linearLayoutAnswer.setOrientation(LinearLayout.HORIZONTAL);

        final AppCompatRadioButton radioButton = new AppCompatRadioButton(QuestionsCreationActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(QuestionsCreationActivity.this, R.color.green)));
        }
        LinearLayout.LayoutParams radioLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioLayoutParams.setMargins(0,22,0,0 );
        radioButton.setLayoutParams(radioLayoutParams);
        radioButtons.add(radioButton);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < radioButtons.size(); i++) {
                    if (!radioButtons.get(i).equals(radioButton)) {
                        radioButtons.get(i).setChecked(false);
                    }
                }
            }
        });

        EditText editText = new EditText(QuestionsCreationActivity.this);
        editText.setTextColor(getResources().getColor(R.color.colorAuthText));
        editText.setHint("Answer");
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.5f));

        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageLayoutParams.setMargins(0,22,0,0 );
        final ImageView btnDelete = new ImageView(QuestionsCreationActivity.this);
        btnDelete.setLayoutParams(imageLayoutParams);
        btnDelete.setImageResource(R.drawable.ic_remove_circle_outline_black_18dp);
        btnDelete.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 8.5f));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout parentLayoutAnswer = (LinearLayout) btnDelete.getParent();
                LinearLayout layoutMain = (LinearLayout) parentLayoutAnswer.getParent();
                layoutMain.removeView(parentLayoutAnswer);

            }
        });

        linearLayoutAnswer.addView(radioButton);
        linearLayoutAnswer.addView(editText);
        linearLayoutAnswer.addView(btnDelete);

        return linearLayoutAnswer;
    }

    private LinearLayout addCheckboxQuestion() {
        Toast.makeText(QuestionsCreationActivity.this, "A few from the list", Toast.LENGTH_SHORT).show();
        LinearLayout linearLayoutAnswer = new LinearLayout(QuestionsCreationActivity.this);
        linearLayoutAnswer.setOrientation(LinearLayout.HORIZONTAL);

        AppCompatCheckBox checkBox = new AppCompatCheckBox(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkBox.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(QuestionsCreationActivity.this, R.color.green)));
        }

        EditText editText = new EditText(QuestionsCreationActivity.this);
        editText.setTextColor(getResources().getColor(R.color.colorAuthText));
        editText.setHint("Answer");
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.5f));

        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageLayoutParams.setMargins(0,22,0,0 );
        final ImageView btnDelete = new ImageView(QuestionsCreationActivity.this);
        btnDelete.setLayoutParams(imageLayoutParams);
        btnDelete.setImageResource(R.drawable.ic_remove_circle_outline_black_18dp);
        btnDelete.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 8.5f));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout parentLayoutAnswer = (LinearLayout) btnDelete.getParent();
                LinearLayout layoutMain = (LinearLayout) parentLayoutAnswer.getParent();
                layoutMain.removeView(parentLayoutAnswer);

            }
        });

        linearLayoutAnswer.addView(checkBox);
        linearLayoutAnswer.addView(editText);
        linearLayoutAnswer.addView(btnDelete);

        return linearLayoutAnswer;
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
