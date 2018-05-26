package com.qit.android.models.quiz;

import com.qit.android.models.answer.Comment;
import com.qit.android.models.user.User;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Interview extends Quiz {

    private List<Comment> comments = new ArrayList<>();
    private User author;

}
