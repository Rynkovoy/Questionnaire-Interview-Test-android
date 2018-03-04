package com.qit.android.models.quiz;

import com.qit.android.models.answer.Answer;
import com.qit.android.models.user.User;

import lombok.Data;

import java.util.Set;

@Data
public class Result {

	private Long id;
	private User author;
	private Quiz quiz;
	private Set<Answer> answers;

}
