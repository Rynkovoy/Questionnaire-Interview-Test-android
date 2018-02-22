package com.qit.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.rest.dto.QuestionnaireDTO;
import com.qit.android.utils.DimensionUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class QuestionnaireCreationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Toolbar toolbar;
    private LinearLayout llQuestionnaire;
    private LinearLayout llPassword;
    private LinearLayout llStartDate;
    private LinearLayout llEndDate;
    private LinearLayout llAnonymity;
    private LinearLayout llAnswersLimit;
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
    }


    private void addTitleHandler() {}

    private void addDescriptionHandler() {}


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int switchId = compoundButton.getId();
        switch (switchId) {
            case R.id.switchPassword:
                createEditText(switchId, isChecked, QitInputType.PASSWORD, null);
                break;
            case R.id.switchStartDate:
                createEditText(switchId, isChecked, QitInputType.TIMESTAMP, null);
                break;
            case R.id.switchEndDate:
                createEditText(switchId, isChecked, QitInputType.TIMESTAMP, null);
                break;
            case R.id.switchIsAnonymity:
                handleAnonymity();
                break;
            case R.id.switchAnswersLimit:
                createEditText(switchId, isChecked, QitInputType.TIMESTAMP, null); //todo change input type to null
                break;
            default:
                return;
        }
    }

    private void handleAnonymity() {

    }

    private void addStartDateHandler() {

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        );
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
        llQuestionnaire = findViewById(R.id.llUnderSroll);

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


    private AppCompatEditText createEditText(int viewId, boolean isChecked, QitInputType inputType, String text) {
        AppCompatEditText appCompatEditText = null;
        SwitchCompat switchCompat = findViewById(viewId);
        LinearLayout parentView = (LinearLayout) switchCompat.getParent();
        int viewPosition = llQuestionnaire.indexOfChild(parentView) + 1;

        if (isChecked) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(
                    DimensionUtils.dp2px(QuestionnaireCreationActivity.this, 16), 0,
                    DimensionUtils.dp2px(QuestionnaireCreationActivity.this, 16), 0);
            appCompatEditText = new AppCompatEditText(QuestionnaireCreationActivity.this);
            appCompatEditText.setLayoutParams(layoutParams);
            View etChild = parentView.getChildAt(0);
            appCompatEditText.setHint(((TextView)etChild).getText());
            appCompatEditText.setTextColor(getResources().getColor(R.color.colorAuthText));
            appCompatEditText.setText(text);

            switch (inputType) {
                case PASSWORD:
                    appCompatEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    appCompatEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    break;
            }

            llQuestionnaire.addView(appCompatEditText, viewPosition);

        } else {
            llQuestionnaire.removeViewAt(viewPosition);
        }


        return appCompatEditText;
    }

    private enum QitInputType {
        PASSWORD, TIMESTAMP
    }
}
