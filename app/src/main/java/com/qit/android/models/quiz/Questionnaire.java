package com.qit.android.models.quiz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Questionnaire extends Quiz {

	private Boolean anonymity;
	private Integer answerLimit;

}
