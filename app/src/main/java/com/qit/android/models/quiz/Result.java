package com.qit.android.models.quiz;

import com.qit.android.models.answer.Answer;
import com.qit.android.models.question.QuestionType;
import com.qit.android.models.user.User;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class Result implements Serializable {

    private boolean answerBool;
    private String answerStr;
    private QuestionType questionType;

}
