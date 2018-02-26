package com.qit.android.utils;


import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qit.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class QitEditTextCreator {

    public static class QuizCreationBuilder implements
            DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener, TimePickerDialog.OnTimeSetListener {

        private AppCompatEditText appCompatEditText;
        private SwitchCompat switchCompat;
        private boolean isChecked;
        private QitInputType inputType;
        private String text;
        private AppCompatActivity activity;
        private LinearLayout mainParent;

        private Calendar now;
        private DatePickerDialog dpd;
        private TimePickerDialog tpd;
        private String strDate;

        private int viewPosition;

        public QuizCreationBuilder addSwitch(SwitchCompat switchCompat) {
            this.switchCompat = switchCompat;
            return this;
        }

        public QuizCreationBuilder addCheck(boolean isChecked) {
            this.isChecked = isChecked;
            return this;
        }

        public QuizCreationBuilder addInputType(QitInputType inputType) {
            this.inputType = inputType;
            return this;
        }

        public QuizCreationBuilder addText(String text) {
           this.text = text;
            return this;
        }

        public QuizCreationBuilder addParent(LinearLayout parent) {
            this.mainParent = parent;
            return this;
        }

        public AppCompatEditText create(AppCompatActivity activity) {
            this.activity = activity;
            return createEditTextForQuizCreation(activity, switchCompat, isChecked, inputType, text, mainParent);
        }

        private AppCompatEditText createEditTextForQuizCreation(AppCompatActivity activity,
                                                                SwitchCompat switchCompat,
                                                                boolean isChecked,
                                                                QitInputType inputType,
                                                                String text,
                                                                LinearLayout mainParent) {

            LinearLayout parentView = (LinearLayout) switchCompat.getParent();
            viewPosition = mainParent.indexOfChild(parentView) + 1;
            if (isChecked) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(
                        DimensionUtils.dp2px(activity, 16), 0,
                        DimensionUtils.dp2px(activity, 16), 0);
                appCompatEditText = new AppCompatEditText(activity);
                appCompatEditText.setLayoutParams(layoutParams);
                View etChild = parentView.getChildAt(0);
                appCompatEditText.setHint(((TextView)etChild).getText());
                appCompatEditText.setTextColor(activity.getResources().getColor(R.color.colorAuthText));
                appCompatEditText.setText(text);

                switch (inputType) {
                    case PASSWORD:
                        appCompatEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        appCompatEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        mainParent.addView(appCompatEditText, viewPosition);
                        break;

                    case TIMESTAMP:
                        appCompatEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                        appCompatEditText.setEnabled(false);
                        initCalendarWidget();
                        dpd.show(activity.getFragmentManager(), "Datepickerdialog");
                        break;
                }

            } else {
                mainParent.removeViewAt(viewPosition);
            }

            return appCompatEditText;
        }

        private void initCalendarWidget() {
            now = Calendar.getInstance();
            dpd = DatePickerDialog.newInstance(this,
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            dpd.setAccentColor(activity.getResources().getColor(R.color.colorGreenMain));
            dpd.setOnCancelListener(this);

            tpd = TimePickerDialog.newInstance(this, true);
            tpd.setAccentColor(activity.getResources().getColor(R.color.colorGreenMain));
            tpd.setOnCancelListener(this);
        }

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if (switchCompat.isChecked()) {
                mainParent.addView(appCompatEditText, viewPosition);
                switchCompat.setChecked(false);
            }
        }

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            strDate = String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear) + "/" + year + " — ";
            tpd.show(activity.getFragmentManager(), "Timepickerdialog");

        }

        @Override
        public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
            strDate += hourOfDay + ":" + String.format("%02d", minute);
            appCompatEditText.setText(strDate);
            mainParent.addView(appCompatEditText, viewPosition);
        }
    }
}
