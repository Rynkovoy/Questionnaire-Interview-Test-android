package com.qit.android.rest.dto;

import java.util.Set;

public class InterviewDTO extends QuizDTO{

    private Integer membersLimit;
    private String password;
    private Boolean resultVisibility;
    private Set<QuestionDTO> questions;

    public InterviewDTO() {
    }

    public Integer getMembersLimit() {
        return membersLimit;
    }

    public void setMembersLimit(Integer membersLimit) {
        this.membersLimit = membersLimit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getResultVisibility() {
        return resultVisibility;
    }

    public void setResultVisibility(Boolean resultVisibility) {
        this.resultVisibility = resultVisibility;
    }

    public Set<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionDTO> questions) {
        this.questions = questions;
    }


    @Override
    public String toString() {
        return "InterviewDTO{" +
                "membersLimit=" + membersLimit +
                ", password='" + password + '\'' +
                ", resultVisibility=" + resultVisibility +
                ", questions=" + questions +
                '}';
    }
}
