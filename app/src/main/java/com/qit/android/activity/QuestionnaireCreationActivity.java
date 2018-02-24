package com.qit.android.activity;

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
import com.qit.android.rest.dto.QuestionnaireDTO;
import com.qit.android.utils.QitEditTextCreator;
import com.qit.android.utils.QitInputType;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class QuestionnaireCreationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Toolbar toolbar;
    private LinearLayout llUnderScroll;
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

    private String strStartDate;

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
                addEditText(switchId, isChecked, QitInputType.PASSWORD, null);
                break;
            case R.id.switchEndDate:
                addEditText(switchId, isChecked, QitInputType.PASSWORD, null);
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

    private String addStartDateHandler() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                strStartDate = dayOfMonth + "/" + monthOfYear + 1 + "/" + year;
                Toast.makeText(QuestionnaireCreationActivity.this, strStartDate, Toast.LENGTH_SHORT).show();
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.colorGreenMain));
        dpd.show(getFragmentManager(), "Datepickerdialog");

        return strStartDate;
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

    private void addEditText(int switchId, boolean isChecked, QitInputType inputType, String text) {
       new QitEditTextCreator.QuizCreationBuilder()
                .addSwitch((SwitchCompat) findViewById(switchId))
                .addCheck(isChecked)
                .addInputType(inputType)
                .addText(text)
                .addParent(llUnderScroll)
                .create(QuestionnaireCreationActivity.this);
    }

}
