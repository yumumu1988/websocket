package com.yumumu.websocket.controller;

import com.yumumu.websocket.constant.GlobalValue;
import com.yumumu.websocket.model.WebSocketRequest;
import com.yumumu.websocket.model.WebSocketResponse;
import com.yumumu.websocket.service.WebSocketService;
import com.yumumu.websocket.util.DestinationUtils;
import com.yumumu.websocket.util.RedisTemplateUtils;
import com.yumumu.websocket.util.SimpMessagingTemplateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class StompWebSocketController {

    private static final Logger log = LoggerFactory.getLogger(StompWebSocketController.class);

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private SimpMessagingTemplateUtils simpMessagingTemplateUtils;

    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    /**
     * 接收所有客户端连接发送的消息，并推送给所有连接的客户端。暂时无业务需要，未使用。
     * @param message
     * @return
     */
    @SuppressWarnings("unchecked")
    @MessageMapping("/v1/ws/message")
    @SendTo("/topic/v1/ws/broadcast")
    public WebSocketResponse receiveMessage(WebSocketRequest message){
        log.info(message.toString());
        WebSocketResponse response = new WebSocketResponse();
        response.setMessage("received client side message");
        return response;
    }

    /**
     * 接收特定的客户端消息，并推送给所有订阅此主题的客户端。
     * @param topic
     * @param webSocketRequest
     * @param headerAccessor
     * @return
     */
    @SuppressWarnings("unchecked")
    @MessageMapping("/v1/ws/{topic}/message")
    public void receiveUpdateMessage(@DestinationVariable String topic, WebSocketRequest webSocketRequest, StompHeaderAccessor headerAccessor){
        log.debug("receive update. topic: " + topic);
        try {
            // 处理接收的客户端数据
            boolean sendToOthers = webSocketService.processWebSocketMessage(webSocketRequest);
            // 是否广播指令集到其他客户端
            if (sendToOthers){
                if (GlobalValue.WEB_SOCKET_CLUSTER_ON){
                    if (GlobalValue.WEB_SOCKET_CLUSTER_STOMP_BROKER_ON){
                        log.debug("......STOMP BROKER MODE......");
                        simpMessagingTemplateUtils.pushMessage(DestinationUtils.generateDestination(topic), webSocketRequest);
                    } else {
                        log.debug("......REDIS MODE......");
                        redisTemplateUtils.publishMessage(topic, webSocketRequest);
                    }
                } else {
                    log.debug("......SIMPLE BROKER MODE......");
                    simpMessagingTemplateUtils.pushMessage(DestinationUtils.generateDestination(topic), webSocketRequest);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("receiveUpdateMessage failed:", e);
        }
    }

    /**
     * 向指定连接通道客户端发送异常消息，未使用。
     * @param e
     * @return
     */
    @MessageExceptionHandler(Exception.class)
    @SendToUser("/v1/ws/errors")
    public WebSocketResponse handleExceptions(Exception e){
        log.error("Stomp WebSocket Exception: ", e);
        WebSocketResponse webSocketResponse = new WebSocketResponse();
        webSocketResponse.setMessage(e.getMessage());
        return webSocketResponse;
    }
}
