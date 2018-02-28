package com.qit.android.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.qit.R;
import com.qit.android.models.Questionnaire;
import com.qit.android.rest.dto.QuestionnaireDTO;
import com.qit.android.utils.QitEditTextCreator;
import com.qit.android.utils.QitInputType;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class QuestionnaireCreationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, TextWatcher {

    private Toolbar toolbar;
    private LinearLayout llUnderScroll;
    private AppCompatEditText etTitle;
    private AppCompatEditText etDescription;
    private SwitchCompat switchPassword;
    private SwitchCompat switchStartDate;
    private SwitchCompat switchEndDate;
    private SwitchCompat switchIsAnonymity;
    private SwitchCompat switchAnswersLimit;
    private Button btnCancel;
    private Button btnNext;
    private Questionnaire questionnaire;
    private AppCompatEditText etPassword;
    private AppCompatEditText etStartDate;
    private AppCompatEditText etEndDate;
    private AppCompatEditText etAnonymity;
    private AppCompatEditText etAnswersLimit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_creation);
        initToolbar();
        initViewComponents();
        initQuestionnaire();
    }


    private void addTitleHandler() {}

    private void addDescriptionHandler() {}

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int switchId = compoundButton.getId();
        switch (switchId) {
            case R.id.switchPassword:
                etPassword = addEditText(switchId, isChecked, QitInputType.PASSWORD, null);
                if (etPassword != null) {
                    etPassword.addTextChangedListener(this);
                }
                break;
            case R.id.switchStartDate:
                etStartDate = addEditText(switchId, isChecked, QitInputType.TIMESTAMP, null);
                if (etStartDate != null) {
                    etStartDate.addTextChangedListener(this);
                }
                break;
            case R.id.switchEndDate:
                etEndDate = addEditText(switchId, isChecked, QitInputType.TIMESTAMP, null);
                if (etEndDate != null) {
                    etEndDate.addTextChangedListener(this);
                }
                break;
            case R.id.switchIsAnonymity:
                if (isChecked) {
                    questionnaire.setAnonymity(true);
                } else {
                    questionnaire.setAnonymity(false);
                }
                break;
            case R.id.switchAnswersLimit:
                etAnswersLimit = addEditText(switchId, isChecked, QitInputType.PASSWORD, null);
                if (etAnswersLimit != null) {
                    etAnswersLimit.addTextChangedListener(this);
                }
                break;
            default:
                return;
        }
    }

    private void handleAnonymity() {

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

    private void initQuestionnaire() {
        questionnaire = new Questionnaire();
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

    private AppCompatEditText addEditText(int switchId, boolean isChecked, QitInputType inputType, String text) {
       return new QitEditTextCreator.QuizCreationBuilder()
                .addSwitch((SwitchCompat) findViewById(switchId))
                .addCheck(isChecked)
                .addInputType(inputType)
                .addText(text)
                .addParent(llUnderScroll)
                .create(QuestionnaireCreationActivity.this);
    }

    private void initViewComponents() {
        llUnderScroll = findViewById(R.id.llUnderSroll);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);

        switchPassword = findViewById(R.id.switchPassword);
        switchPassword.setOnCheckedChangeListener(this);

        switchStartDate = findViewById(R.id.switchStartDate);
        switchStartDate.setOnCheckedChangeListener(this);

        switchEndDate = findViewById(R.id.switchEndDate);
        switchEndDate.setOnCheckedChangeListener(this);

        switchIsAnonymity = findViewById(R.id.switchIsAnonymity);
        switchIsAnonymity.setOnCheckedChangeListener(this);

        switchAnswersLimit = findViewById(R.id.switchAnswersLimit);
        switchAnswersLimit.setOnCheckedChangeListener(this);

        btnCancel = findViewById(R.id.btnCancel);
        btnNext = findViewById(R.id.btnNext);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
       /* if (etPassword != null && editable == etPassword.getEditableText()) {
            if (switchPassword.isChecked()) {
                questionnaire.setPassword(String.valueOf(editable));
            } else {
                questionnaire.setPassword(null);
            }
        } else if (etStartDate != null && editable == etStartDate.getEditableText()) {

        } else if (etEndDate != null && editable == etEndDate.getEditableText()) {

        } else if (etAnswersLimit != null && editable == etAnswersLimit.getEditableText()) {
            if (switchAnswersLimit.isChecked()) {
                questionnaire.setAnswerLimit(Integer.parseInt(String.valueOf(editable)));
            } else {
                questionnaire.setAnswerLimit(null);
            }
        }*/
    }

    public void handleBtnNext(View view) {

        if (switchPassword.isChecked()) {
            questionnaire.setPassword(String.valueOf(etPassword.getText()));
        } else {
            questionnaire.setPassword(null);
        }

        questionnaire.setAnonymity(switchIsAnonymity.isChecked());

        if (switchStartDate.isChecked()) {
            String strDate = String.valueOf(etStartDate.getText());
            Format formatter = new SimpleDateFormat("dd/MM/yyyy — hh:mm");
            Date date;
            try {
                date = ((DateFormat) formatter).parse(strDate);
                questionnaire.setStartDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            questionnaire.setStartDate(new Date());
        }

        if (switchEndDate.isChecked()) {
            String strDate = String.valueOf(switchEndDate.getText());
            Format formatter = new SimpleDateFormat("dd/MM/yyyy — hh:mm");
            Date date;
            try {
                date = ((DateFormat) formatter).parse(strDate);
                questionnaire.setEndDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            questionnaire.setStartDate(null);
        }

        if (switchAnswersLimit.isChecked()) {
            questionnaire.setAnswerLimit(Integer.parseInt(String.valueOf(etAnswersLimit.getText())));
        } else {
            questionnaire.setAnswerLimit(null);
        }
    }

}












