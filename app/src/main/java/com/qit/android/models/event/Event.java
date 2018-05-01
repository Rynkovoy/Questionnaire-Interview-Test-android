package com.qit.android.models.event;

import com.qit.android.models.chat.Message;
import com.qit.android.models.quiz.Interview;
import com.qit.android.models.quiz.Questionnaire;

import java.util.ArrayList;
import java.util.List;

public class Event {

    private String fullHeader;
    private String fullDetails;
    private String date;

    private String eventPassword;

    private String eventOwnerName;
    private String eventOwner;

    private List<String> eventModerators = new ArrayList<>();

    private List<String> eventCoop = new ArrayList<>();

    private List<Questionnaire> questionnaireList = new ArrayList<>();
    private List<Interview> interviewsList = new ArrayList<>();
    private List<Message> messageList = new ArrayList<>();

    private boolean isEventOpened = true;
    private boolean isNewUserInEventeNeedToBeConfirmed = false;

    public Event(String fullHeader, String fullDetails, String date, String eventPassword, String eventOwner, List<String> eventModerators, List<String> eventCoop, boolean isEventOpened, boolean isNewUserInEventeNeedToBeConfirmed) {
        this.fullHeader = fullHeader;
        this.fullDetails = fullDetails;
        this.date = date;
        this.eventPassword = eventPassword;
        this.eventOwner = eventOwner;
        this.eventModerators = eventModerators;
        this.eventCoop = eventCoop;
        this.isEventOpened = isEventOpened;
        this.isNewUserInEventeNeedToBeConfirmed = isNewUserInEventeNeedToBeConfirmed;
    }

    public Event() {
    }


    public String getFullHeader() {
        return fullHeader;
    }

    public void setFullHeader(String fullHeader) {
        this.fullHeader = fullHeader;
    }

    public String getFullDetails() {
        return fullDetails;
    }

    public void setFullDetails(String fullDetails) {
        this.fullDetails = fullDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventPassword() {
        return eventPassword;
    }

    public void setEventPassword(String eventPassword) {
        this.eventPassword = eventPassword;
    }

    public String getEventOwner() {
        return eventOwner;
    }

    public void setEventOwner(String eventOwner) {
        this.eventOwner = eventOwner;
    }

    public List<String> getEventModerators() {
        return eventModerators;
    }

    public void setEventModerators(List<String> eventModerators) {
        this.eventModerators = eventModerators;
    }

    public List<String> getEventCoop() {
        return eventCoop;
    }

    public void setEventCoop(List<String> eventCoop) {
        this.eventCoop = eventCoop;
    }

    public boolean isEventOpened() {
        return isEventOpened;
    }

    public void setEventOpened(boolean eventOpened) {
        isEventOpened = eventOpened;
    }

    public boolean isNewUserInEventeNeedToBeConfirmed() {
        return isNewUserInEventeNeedToBeConfirmed;
    }

    public void setNewUserInEventeNeedToBeConfirmed(boolean newUserInEventeNeedToBeConfirmed) {
        isNewUserInEventeNeedToBeConfirmed = newUserInEventeNeedToBeConfirmed;
    }

    public String getEventOwnerName() {
        return eventOwnerName;
    }

    public void setEventOwnerName(String eventOwnerName) {
        this.eventOwnerName = eventOwnerName;
    }

    public List<Questionnaire> getQuestionLists() {
        return questionnaireList;
    }

    public void setQuestionLists(List<Questionnaire> questionLists) {
        this.questionnaireList = questionLists;
    }


    public List<Interview> getInterviewsList() {
        return interviewsList;
    }

    public void setInterviewsList(List<Interview> interviewsList) {
        this.interviewsList = interviewsList;
    }


    public List<Questionnaire> getQuestionnaireList() {
        return questionnaireList;
    }

    public void setQuestionnaireList(List<Questionnaire> questionnaireList) {
        this.questionnaireList = questionnaireList;
    }

    public List<Message> getMassegeList() {
        return messageList;
    }

    public void setMassegeList(List<Message> messageList) {
        this.messageList = messageList;
    }



}
