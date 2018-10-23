package com.yumumu.websocket.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SimpMessagingTemplateUtils {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public SimpMessagingTemplateUtils(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /*
     * 发送消息到基于某个用户建立的websocket连接(点对点)
     * */
    public void pushMessageToRelatedUsers(String username, String destination, Serializable serializable){
//      eg.  simpMessagingTemplate.convertAndSendToUser("username", "/v1/editor/courseware/command-set", coursewareEditorRequest);
//        最终的地址为 "/special-user/ws/username/v1/editor/courseware/command-set"。"/special-user/"为StompWebSocketConfig中配置的registry.setUserDestinationPrefix("/special-user/ws/");
        simpMessagingTemplate.convertAndSendToUser(username, destination, serializable);
    }

    /*
     * 发送消息到订阅某个目的地的websocket连接
     * */
    public void pushMessage(String destination, Serializable serializable){
//      eg.  simpMessagingTemplate.convertAndSend("/topic/greetings", helloMessage);
        simpMessagingTemplate.convertAndSend(destination, serializable);
    }
}
