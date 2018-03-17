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
	private String gender;


    public User(String login, String password, boolean isEnabled, String firstName, String lastName, String phoneNumber, Date birthday, String additionalInfo, String gender) {
        this.login = login;
        this.password = password;
        this.isEnabled = isEnabled;
        this.firstName = firstName;
        this.lastName = lastName;
        //this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.additionalInfo = additionalInfo;
        this.gender = gender;
    }

    public User(){

    }

}
