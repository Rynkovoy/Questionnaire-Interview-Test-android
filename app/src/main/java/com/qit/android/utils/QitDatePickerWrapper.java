package com.qit.android.utils;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.qit.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class QitDatePickerWrapper implements DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener {

    private AppCompatActivity activity;
    private AppCompatEditText editText;

    private Calendar now;
    private DatePickerDialog dpd;
    private StringBuilder stringDate;
    private boolean canceled;
    private SwitchCompat  switchCompat;

    public QitDatePickerWrapper(AppCompatActivity activity) {
        this.activity = activity;
        this.stringDate = new StringBuilder();
        this.canceled = false;

        now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setAccentColor(activity.getResources().getColor(R.color.colorGreenMain));
        dpd.setOnCancelListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        stringDate.append(dayOfMonth + "/" + monthOfYear + 1 + "/" + year);
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        switchCompat.setChecked(false);
    }

    public void show() {
        canceled = false;
        dpd.show(activity.getFragmentManager(), "Datepickerdialog");
    }

    public void setSwitchOnCancel(int switchId) {
        switchCompat = activity.findViewById(switchId);
    }

    public String getStringDate() {
        return stringDate.toString();
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setEditText(AppCompatEditText editText) {
        this.editText = editText;
    }
}
