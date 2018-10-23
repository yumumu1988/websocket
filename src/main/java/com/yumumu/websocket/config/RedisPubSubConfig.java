package com.yumumu.websocket.config;

import com.yumumu.websocket.condition.WebSocketClusterByRedisCondition;
import com.yumumu.websocket.constant.GlobalValue;
import com.yumumu.websocket.model.WebSocketRequestWrapper;
import com.yumumu.websocket.util.DestinationUtils;
import com.yumumu.websocket.util.SimpMessagingTemplateUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

@Configuration
public class RedisPubSubConfig {

    @Resource
    private SimpMessagingTemplateUtils simpMessagingTemplateUtils;

    @Resource
    private RedisTemplate redisTemplate;

    @Bean
    @Conditional(WebSocketClusterByRedisCondition.class)
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
//        可扩展支持多个特殊发布队列
        redisMessageListenerContainer.addMessageListener(messageListener(), new ChannelTopic(GlobalValue.WEB_SOCKET_CHANNEL));
        return redisMessageListenerContainer;
    }

    @Bean
    @ConditionalOnBean(RedisMessageListenerContainer.class)
    public MessageListener messageListener(){
        MessageListener messageListener = new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                WebSocketRequestWrapper wrapper = (WebSocketRequestWrapper) redisTemplate.getValueSerializer().deserialize(message.getBody());
                String channelName = (String) redisTemplate.getStringSerializer().deserialize(message.getChannel());
                String destination = DestinationUtils.generateDestination(wrapper.getTopic());
                simpMessagingTemplateUtils.pushMessage(destination, wrapper.getWebSocketRequest());
            }
        };
        return messageListener;
    }
}
