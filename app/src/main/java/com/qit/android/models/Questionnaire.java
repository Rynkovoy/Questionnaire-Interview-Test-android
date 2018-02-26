package com.qit.android.models;

import lombok.Data;

@Data
public class Questionnaire extends Quiz {

    private Boolean anonymity;
    private Integer answerLimit;

}
