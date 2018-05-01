package com.qit.android.models.chat;

import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;

import lombok.Data;

@Data
public class ChatUser implements Serializable {

    private String id;
    private String name;
    private String avatar;
    private boolean online;

    public ChatUser () {};

    public ChatUser(String id, String name, String avatar, boolean online) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.online = online;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
}
