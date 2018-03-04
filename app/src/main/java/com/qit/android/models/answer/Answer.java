package com.qit.android.models.answer;

import com.qit.android.models.question.Question;
import com.qit.android.models.quiz.Result;

import lombok.Data;

import java.util.Set;

@Data
public class Answer {

	private Long id;
	private Result result;
	private Question question;
	private Set<Variant> variants;
}
