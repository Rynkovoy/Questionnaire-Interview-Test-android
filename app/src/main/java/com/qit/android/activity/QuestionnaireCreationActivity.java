package com.qit.android.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.models.event.Event;
import com.qit.android.models.question.Question;
import com.qit.android.models.quiz.Questionnaire;
import com.qit.android.models.user.User;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.utils.QitEditTextCreator;
import com.qit.android.utils.QitInputType;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private TextView btnCancel;
    private TextView btnNext;
    private Questionnaire questionnaire;
    private AppCompatEditText etPassword;
    private AppCompatEditText etStartDate;
    private AppCompatEditText etEndDate;
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
//            case R.id.switchPassword:
//                etPassword = addEditText(switchId, isChecked, QitInputType.PASSWORD, null);
//                break;
            case R.id.switchStartDate:
                etStartDate = addEditText(switchId, isChecked, QitInputType.TIMESTAMP, null);
                break;
            case R.id.switchEndDate:
                etEndDate = addEditText(switchId, isChecked, QitInputType.TIMESTAMP, null);
                break;
            case R.id.switchIsAnonymity:
                break;
            case R.id.switchAnswersLimit:
                etAnswersLimit = addEditText(switchId, isChecked, QitInputType.LIMIT, null);
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
        switchEndDate.setOnCheckedChangeListener(this);

        switchAnswersLimit = findViewById(R.id.switchAnswersLimit);
        switchAnswersLimit.setOnCheckedChangeListener(this);

        btnCancel = findViewById(R.id.btnCancel);
        btnNext = findViewById(R.id.btnNext);

    }


    public void handleBtnNext(View view) {
        questionnaire.setSummary(String.valueOf(etTitle.getText()));
        questionnaire.setDescription(String.valueOf(etDescription.getText()));

        if (etPassword != null && switchPassword.isChecked()) {
            questionnaire.setPassword(String.valueOf(etPassword.getText()));
        } else {
            questionnaire.setPassword(null);
        }

        questionnaire.setAnonymity(switchIsAnonymity.isChecked());

        if (etStartDate != null && switchStartDate.isChecked()) {
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

        if (etEndDate != null && switchEndDate.isChecked()) {
            String strDate = String.valueOf(etEndDate.getText());
            Format formatter = new SimpleDateFormat("dd/MM/yyyy — hh:mm");
            Date date;
            try {
                date = ((DateFormat) formatter).parse(strDate);
                questionnaire.setEndDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            questionnaire.setStartDate(new Date());
        }

        if (etAnswersLimit != null && switchAnswersLimit.isChecked()) {
            questionnaire.setAnswerLimit(Integer.parseInt(String.valueOf(etAnswersLimit.getText())));
        } else {
            questionnaire.setAnswerLimit(null);
        }

        //todo change to user from realm
        //User user = new User();
        //user.setLogin("rynkovoy");

        // TODO NEED TO BE IN OTHER CLASS LIKE FIREBASE GET USER
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user"+"/"+mAuth.getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                questionnaire.setAuthor(user);

                Intent intent = new Intent(QuestionnaireCreationActivity.this, QuestionsCreationActivity.class);
                intent.putExtra("Questionnaire", questionnaire);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void handleBtnCancel(View view) {
        createAndShowAlertDialog();
    }

    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MaterialBaseTheme_AlertDialog);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(QuestionnaireCreationActivity.this, QitActivity.class));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}












