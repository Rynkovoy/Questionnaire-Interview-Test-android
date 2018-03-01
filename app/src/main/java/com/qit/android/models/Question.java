package com.qit.android.models;

import java.util.Set;

import lombok.Data;

@Data
public class Question {

    private Long id;
    private String text;
    private boolean isNecessary;
    private QuestionType questionType;
    private Quiz quiz;
    private Set<Variant> variants;
}
