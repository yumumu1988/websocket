package com.yumumu.websocket.model;

import java.io.Serializable;

public class WebSocketRequestWrapper implements Serializable {
    private static final long serialVersionUID = 5362041281674856593L;
    private String topic;
    private WebSocketRequest webSocketRequest;

    public WebSocketRequestWrapper() {
    }

    public WebSocketRequestWrapper(String topic, WebSocketRequest webSocketRequest) {
        this.topic = topic;
        this.webSocketRequest = webSocketRequest;
    }

    public WebSocketRequest getWebSocketRequest() {
        return webSocketRequest;
    }

    public void setWebSocketRequest(WebSocketRequest webSocketRequest) {
        this.webSocketRequest = webSocketRequest;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
