package com.qit.android.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.models.user.Gender;
import com.qit.android.models.user.User;
import com.qit.android.rest.utils.QitFirebaseUserCreation;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView saveBtn;
    private Button calendarBtn;

    private AppCompatEditText loginEditText;
    private AppCompatEditText passFirstEditText;
    private AppCompatEditText passSecEditText;
    private AppCompatEditText firstNameEditText;
    private AppCompatEditText lastNameEditText;
    private AppCompatEditText phoneEditText;
    public static AppCompatEditText birthDayEditText;
    private AppCompatSpinner genderSpinner;
    private AppCompatEditText userInfoEditText;

    private boolean isRegistrationCHangedFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        try {
            Intent intent = getIntent();
            isRegistrationCHangedFlag = intent.getExtras().getBoolean("isRegistrationCHangedFlag");
        } catch (Exception e) {
            e.printStackTrace();
        }

        UiElemInitialise();

        saveBtn.setOnClickListener(this);
        calendarBtn.setOnClickListener(this);
    }


    private void UiElemInitialise() {
        saveBtn = findViewById(R.id.regBtnSave);
        calendarBtn = findViewById(R.id.regBtnCalendar);

        loginEditText = findViewById(R.id.regUserLogin);
        passFirstEditText = findViewById(R.id.regUserPassFirst);
        passSecEditText = findViewById(R.id.regUserPassSec);
        firstNameEditText = findViewById(R.id.regUserFirstName);
        lastNameEditText = findViewById(R.id.regUserLastName);
        phoneEditText = findViewById(R.id.regUserPhoneNum);
        userInfoEditText = findViewById(R.id.regUserInfo);

        birthDayEditText = findViewById(R.id.regUserBirthDay);
        birthDayEditText.addTextChangedListener(birthDayCorrector(birthDayEditText));

        genderSpinner = findViewById(R.id.regUserGender);
        {
            List<String> list = new ArrayList<String>();
            list.add(Gender.MALE.toString());
            list.add(Gender.FEMALE.toString());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genderSpinner.setAdapter(adapter);
        }

        if (isRegistrationCHangedFlag) {
            loginEditText.setFocusable(false);
            loginEditText.setEnabled(false);
            loginEditText.setCursorVisible(false);
            loginEditText.setKeyListener(null);

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = mAuth.getCurrentUser();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("user"+"/"+firebaseUser.getUid());


            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    loginEditText.setText(user.getLogin());
                    passFirstEditText.setText(user.getPassword());
                    passSecEditText.setText(user.getPassword());
                    firstNameEditText.setText(user.getFirstName());
                    lastNameEditText.setText(user.getLastName());
                    phoneEditText.setText(user.getPhoneNumber());
                    userInfoEditText.setText(user.getAdditionalInfo());


                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
                    Date convertedDate = new Date();
                    try {
                        convertedDate = dateFormat.parse(user.getBirthday().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    birthDayEditText.setText(convertedDate.toString());

                    saveBtn.setText("Save Edited Profile");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }

    public TextWatcher birthDayCorrector(final AppCompatEditText birthDayEditText) {
        final TextWatcher txwatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("TAG", start + " " + " " + count);
                if (count == 1) {
                    if (start == 1) {
                        birthDayEditText.getText().insert(birthDayEditText.getSelectionStart(), ".");
                    } else if (start == 4) {
                        birthDayEditText.getText().insert(birthDayEditText.getSelectionStart(), ".");
                    }
                }
            }

            public void afterTextChanged(Editable s) {

            }
        };
        return txwatcher;
    }


    private boolean samePassCheck(String aPass, String bPass) {
        return aPass.equals(bPass);
    }

    private Date parseStringToDate(String birthDay) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(birthDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public boolean isFilledFields(String login, String pass, String firstName, String lastName, String phone, String birthDay) {
        return !(login.equalsIgnoreCase("") || pass.equalsIgnoreCase("")
                || firstName.equalsIgnoreCase("") || lastName.equalsIgnoreCase("") ||
                phone.equalsIgnoreCase("") || birthDay.equalsIgnoreCase(""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.regBtnSave): {
//                Log.i("LOG", loginEditText.getText().toString() + " " + passFirstEditText.getText().toString() + " " +
//                        firstNameEditText.getText().toString() + " " + lastNameEditText.getText().toString() + " " +
//                        phoneEditText.getText().toString() + " " + birthDayEditText.getText().toString());

                if (isFilledFields(loginEditText.getText().toString(), passFirstEditText.getText().toString(),
                        firstNameEditText.getText().toString(), lastNameEditText.getText().toString(),
                        phoneEditText.getText().toString(), birthDayEditText.getText().toString())) {

                    if (samePassCheck(passFirstEditText.getText().toString(), passSecEditText.getText().toString())) {

                        if (isValidEmail(loginEditText.getText().toString())) {

                            String gender;
                            if (genderSpinner.getItemAtPosition(genderSpinner.getSelectedItemPosition()).toString().equalsIgnoreCase(Gender.MALE.toString())) {
                                gender = Gender.MALE.toString();
                            } else if (genderSpinner.getItemAtPosition(genderSpinner.getSelectedItemPosition()).toString().equalsIgnoreCase(Gender.FEMALE.toString())) {
                                gender = Gender.MALE.toString();
                            } else {
                                gender = "NO GENDER";
                            }

                            User user = new User(
                                    loginEditText.getText().toString(), passFirstEditText.getText().toString(),
                                    true, firstNameEditText.getText().toString(),
                                    lastNameEditText.getText().toString(), phoneEditText.getText().toString(),
                                    parseStringToDate(birthDayEditText.getText().toString()), userInfoEditText.getText().toString(),
                                    gender/*, null, null*/
                            );

                            pushUser(user);
                            //TODO: CHECK, IF DB HAS SAME LOGIN (EMAIL)!!!

                        } else {
                            Toast.makeText(this, "E-mail not valid!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Password is not the same, check it once more!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Some fields are not filled!", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case (R.id.regBtnCalendar): {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MyDialog);
                newFragment.show(getFragmentManager(), "DatePicker");
                break;
            }

            default: {
                break;
            }
        }
    }

    public void pushUser(final User user) {

        ProgressDialog dialog = new ProgressDialog(RegistrationActivity.this);
        dialog.setMessage("Processing...");
        dialog.show();

//        Intent i = new Intent(RegistrationActivity.this, QitActivity.class);

//        View sharedView = findViewById(R.id.regLogoImg);
//        String transitionName = "appLogo";
//
//        ActivityOptions transitionActivityOptions = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(RegistrationActivity.this, sharedView, transitionName);
//        }

        onBackPressed();
        if (!isRegistrationCHangedFlag) {
            Toast.makeText(this, "Please fill e-mail and password again", Toast.LENGTH_SHORT).show();
        }
        QitFirebaseUserCreation qitFirebaseUserCreation = new QitFirebaseUserCreation();
        qitFirebaseUserCreation.registerUser(user, this, dialog);

    }


    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            if (month < 10) {
                birthDayEditText.setText(day + ".0" + month + "." + year);
            } else {
                birthDayEditText.setText(day + "." + month + "." + year);
            }
        }

    }
}
