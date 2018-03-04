package com.qit.android.models.quiz;

import com.qit.android.models.user.User;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public abstract class Quiz implements Serializable {

	private Long id;
	private String summary;
	private String description;
	private String password;
	private User author;
	private Date startDate;
	private Date endDate;

}
