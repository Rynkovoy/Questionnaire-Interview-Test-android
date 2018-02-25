package com.qit.android.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qit.R;
import com.qit.android.events.CancelDatePickerDialog;
import com.qit.android.rest.dto.QuestionnaireDTO;
import com.qit.android.utils.QitDatePickerWrapper;
import com.qit.android.utils.QitEditTextCreator;
import com.qit.android.utils.QitInputType;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

public class QuestionnaireCreationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

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
                addEditText(switchId, isChecked, QitInputType.PASSWORD, null);
                break;
            case R.id.switchStartDate:
                switchStartDate.getTag();
                addEditText(switchId, isChecked, QitInputType.TIMESTAMP, null);
                break;
            case R.id.switchEndDate:
                addEditText(switchId, isChecked, QitInputType.TIMESTAMP, null);
                break;
            case R.id.switchIsAnonymity:
                handleAnonymity();
                break;
            case R.id.switchAnswersLimit:
                addEditText(switchId, isChecked, QitInputType.PASSWORD, null);
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
}
