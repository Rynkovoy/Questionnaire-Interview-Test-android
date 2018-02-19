package com.qit.android.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qit.R;
import com.qit.android.rest.dto.QuestionnaireDTO;
import com.qit.android.utils.DimensionUtils;

public class QuestionnaireCreationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout llQuestionnaire;
    private AppCompatEditText etTitle;
    private AppCompatEditText etDescription;
    private SwitchCompat switchPassword;
    private SwitchCompat switchStartDate;
    private SwitchCompat switchEndDate;
    private SwitchCompat switchIsAnonymity;
    private SwitchCompat switchAnswersLimit;
    private Button btnCancel;
    private Button btnNext;
    private QuestionnaireDTO questionnaireDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_creation);
        initToolbar();
        initViewComponents();
        addPasswordHandler();
    }


    private void addTitleHandler() {}

    private void addDescriptionHandler() {}

    private void addPasswordHandler() {
        switchPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                TextInputLayout textInputLayout = new TextInputLayout(QuestionnaireCreationActivity.this);

                if (isChecked) {
                  /*  LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(16, 0,
                            16, 0);

                    textInputLayout.setLayoutParams(layoutParams);
                    textInputLayout.setHintTextAppearance(R.style.HintTextLight);*/

                    AppCompatEditText etPassword = new AppCompatEditText(QuestionnaireCreationActivity.this);
                    etPassword.setHint(R.string.auth_password);
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPassword.setTextColor(getResources().getColor(R.color.colorAuthText));

//                    textInputLayout.addView(etPassword);
                    llQuestionnaire.addView(etPassword, 5);

                } else {
                    llQuestionnaire.removeViewAt(5);
                }
            }
        });
    }


    private void initToolbar() {
        toolbar = findViewById(R.id.toolbarQuestionnaireCreation);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_create_questionnaire));
        }
    }

    private void initViewComponents() {
        llQuestionnaire= findViewById(R.id.llQuestionnaire);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        switchPassword = findViewById(R.id.switchPassword);
        switchStartDate = findViewById(R.id.switchStartDate);
        switchEndDate = findViewById(R.id.switchEndDate);
        switchIsAnonymity = findViewById(R.id.switchIsAnonymity);
        switchAnswersLimit = findViewById(R.id.switchAnswersLimit);
        btnCancel = findViewById(R.id.btnCancel);
        btnNext = findViewById(R.id.btnNext);
    }

    private void initQuestionnaire() {
        questionnaireDTO = new QuestionnaireDTO();
        questionnaireDTO.setTitle(etTitle.getText().toString());
        questionnaireDTO.setTopic(etDescription.getText().toString());
    }

    public void handleBtnNext(View view) {
    }

    private void slideDown(View view, float toYDelta){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                toYDelta,                 // fromYDelta
                0); // toYDelta
        animate.setDuration(2000);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
}
