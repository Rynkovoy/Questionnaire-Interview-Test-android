package com.qit.android.models;

import java.sql.Date;
import java.util.Set;

import lombok.Data;

@Data
public class User {

    private String login;
    private String password;
    private boolean isEnabled = true;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date birthday;
    private String additionalInfo;
    private Gender gender;
    private Set<Result> results;

}
