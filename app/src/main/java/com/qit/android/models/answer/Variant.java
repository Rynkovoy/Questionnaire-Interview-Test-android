package com.qit.android.models.answer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Variant {

	private Long id;
	private Long questionId;
	private String text;
}
