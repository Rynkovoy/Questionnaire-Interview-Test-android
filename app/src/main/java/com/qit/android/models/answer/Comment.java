package com.qit.android.models.answer;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Comment implements Serializable {

    private String commentCreatorName;
    private String comment;
    private Date dateOfComment;

}

