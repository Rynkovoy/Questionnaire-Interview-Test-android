package com.qit.android.models.chat;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Message implements Serializable {

    private String id;
    private String text;
    private Date createdAt;
    private ChatUser user;

    public Message(){}

    public Message(String id, ChatUser user, String text) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = new Date();
        // this(id, user, text, new Date());
    }

    public Message(String id, ChatUser user, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public ChatUser getUser() {
        return user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
