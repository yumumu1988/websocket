package com.yumumu.websocket.model;

import java.security.Principal;
import java.util.List;

public class MySimpUser implements Principal {

    private String username;
    private String password;
    private String token;
    private String groupId;
    private String sessionId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getName() {
        if (this.sessionId == null){
            return null;
        }
        return this.sessionId;
    }
}
