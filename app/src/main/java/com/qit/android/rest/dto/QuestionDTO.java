package com.qit.android.rest.dto;


import java.util.Set;
@Deprecated
public class QuestionDTO {

    private Long quizId;
    private Long questionId;
    private String summary;
    private Integer sequence;
    private Set<AnswerDTO> qitAnswers;
    private Set<AnswerVariantDTO> answerVariants;


    public QuestionDTO() {
    }


    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Set<AnswerDTO> getQitAnswers() {
        return qitAnswers;
    }

    public void setQitAnswers(Set<AnswerDTO> qitAnswers) {
        this.qitAnswers = qitAnswers;
    }

    public Set<AnswerVariantDTO> getAnswerVariants() {
        return answerVariants;
    }

    public void setAnswerVariants(Set<AnswerVariantDTO> answerVariants) {
        this.answerVariants = answerVariants;
    }
}
