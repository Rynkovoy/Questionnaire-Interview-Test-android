package com.qit.android.models.quiz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qit.android.models.answer.Answer;
import com.qit.android.models.question.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Questionnaire extends Quiz{

	private Boolean anonymity;
	private Integer answerLimit;

	private List<Question> questionList = new ArrayList<>();

}
