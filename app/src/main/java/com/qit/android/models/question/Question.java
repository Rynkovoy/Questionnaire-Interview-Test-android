package com.qit.android.models.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qit.android.models.answer.Variant;
import com.qit.android.models.quiz.Quiz;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {

	private Long id;
	private Boolean isNecessary = false;
	private String text;
	private Long quizId;
	private String questionType;

	private List<Variant> variants;

	public Question() {
		variants = new ArrayList<>();
	}

	public void addAnswerVariant(Variant variant) {
		variants.add(variant);
	}

	public void removeAnswerVariant(Variant variant) {
		variants.remove(variant);
	}

	public void removeAnswerVariant(int position) {
		variants.remove(position);
	}

}
