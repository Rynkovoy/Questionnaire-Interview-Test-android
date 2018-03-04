package com.qit.android.models.user;

import com.qit.android.models.quiz.Quiz;
import com.qit.android.models.quiz.Result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class User implements Serializable{

	private String login;
	private String password;
	private boolean isEnabled = true;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Date birthday;
	private String additionalInfo;
	private Gender gender;
	private Set<Quiz> quizzes;
	private Set<Result> results;

}
