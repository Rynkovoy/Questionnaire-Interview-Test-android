package com.qit.android.models.answer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Variant implements Serializable {

	private Long id;
	private Long questionId;
	private String text;
	private List<Answer> answers = new ArrayList<>();
}
