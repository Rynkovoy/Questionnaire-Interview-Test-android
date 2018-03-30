package com.qit.android.models.answer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Variant implements Serializable {

	private Long id;
	private Long questionId;
	private String text;
}
