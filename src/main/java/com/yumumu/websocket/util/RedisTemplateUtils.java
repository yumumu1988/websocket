package com.yumumu.websocket.util;

import com.yumumu.websocket.constant.GlobalValue;
import com.yumumu.websocket.model.WebSocketRequest;
import com.yumumu.websocket.model.WebSocketRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisTemplateUtils {
    private static final Logger log = LoggerFactory.getLogger(RedisTemplateUtils.class);

    @Resource
    private RedisTemplate redisTemplate;

    public void publishMessage(String topic, WebSocketRequest webSocketRequest){
        try {
            WebSocketRequestWrapper wrapper = new WebSocketRequestWrapper(topic, webSocketRequest);
            redisTemplate.convertAndSend(GlobalValue.WEB_SOCKET_CHANNEL, wrapper);
        } catch (Exception e){
            log.error("publishMessage failed", e);
        }
    }
}
