package com.yumumu.websocket.service;

import com.yumumu.websocket.model.WebSocketRequest;

public interface WebSocketService {
    default boolean processWebSocketMessage(WebSocketRequest webSocketRequest) {
        return true;
    }
}
