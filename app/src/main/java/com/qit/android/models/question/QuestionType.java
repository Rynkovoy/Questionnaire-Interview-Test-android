package com.qit.android.models.question;

import com.fasterxml.jackson.annotation.JsonValue;


public enum QuestionType {
	CHECKBOX("CHECKBOX"),
	RADIO("RADIO");

	private String questionType;

	QuestionType(String questionType) {
		this.questionType = questionType;
	}

	@Override
	public String toString() {
		return questionType;
	}
}
