package com.qit.android.events;


public class SwitchCheckerEvent {

    private String tag;
    private boolean checked;

    public SwitchCheckerEvent(boolean checked, String tag) {
        this.checked = checked;
        this.tag = tag;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
