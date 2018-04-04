package com.qit.android.models.answer;

import com.qit.android.models.question.Question;
import com.qit.android.models.quiz.Result;
import com.qit.android.models.user.User;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class Answer implements Serializable{

	private String answerCreatedByUser;
	private List<Result> results= new ArrayList<>();
}
