package com.qit.android.models.event;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ConfirmedUser implements Serializable {

    private String id;
    private boolean isConfirmed;
    private Date askDate;

    public ConfirmedUser(String Uid, Date dateOfAsk){
        this.id = Uid;
        isConfirmed = false;
        this.askDate = dateOfAsk;
    }

    public ConfirmedUser(){
    }

}
