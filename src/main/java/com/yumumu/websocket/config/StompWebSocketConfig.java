package com.yumumu.websocket.config;

import com.yumumu.websocket.constant.GlobalValue;
import com.yumumu.websocket.model.MySimpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import javax.annotation.Resource;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static final Logger log = LoggerFactory.getLogger(StompWebSocketConfig.class);
    private static final long SystemHeartbeatSendInterval = 30000;
    private static final long SystemHeartbeatReceiveInterval = 30000;

    @Resource
    private GlobalValue globalValue;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
//        客户端连接服务端的注册地址
        String endPoint = "/stomp-websocket/v1/end-point";
        if (StringUtils.isEmpty(GlobalValue.WEB_SOCKET_ALLOWED_ORIGINS)){
            stompEndpointRegistry.addEndpoint(endPoint).withSockJS();
        } else {
            stompEndpointRegistry.addEndpoint(endPoint).setAllowedOrigins(GlobalValue.WEB_SOCKET_ALLOWED_ORIGINS).withSockJS();
        }
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        if (GlobalValue.WEB_SOCKET_CLUSTER_ON && GlobalValue.WEB_SOCKET_CLUSTER_STOMP_BROKER_ON){
            registry.
                    enableStompBrokerRelay("/topic").
                    setSystemLogin(GlobalValue.WEB_SOCKET_CLUSTER_STOMP_BROKER_SYS_LOGIN).
                    setSystemPasscode(GlobalValue.WEB_SOCKET_CLUSTER_STOMP_BROKER_SYS_PWD).
                    setClientLogin(GlobalValue.WEB_SOCKET_CLUSTER_STOMP_BROKER_CLI_LOGIN).
                    setClientPasscode(GlobalValue.WEB_SOCKET_CLUSTER_STOMP_BROKER_CLI_PWD).
                    setRelayHost(GlobalValue.WEB_SOCKET_CLUSTER_STOMP_BROKER_HOST).
                    setRelayPort(GlobalValue.WEB_SOCKET_CLUSTER_STOMP_BROKER_PORT).
                    setSystemHeartbeatReceiveInterval(SystemHeartbeatReceiveInterval).
                    setSystemHeartbeatSendInterval(SystemHeartbeatSendInterval);
        } else {
            ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
            threadPoolTaskScheduler.setPoolSize(10);
            threadPoolTaskScheduler.setDaemon(true);
            threadPoolTaskScheduler.initialize();
//        客户端订阅服务端推送消息的地址（群发）heartbeat中第一个参数为server->client的间隔时间，第二个参数为client->server的间隔时间。设置为0表示不发送heartbeat。taskScheduler只适用于simpleBroker
            registry.enableSimpleBroker("/topic/").setHeartbeatValue(new long[]{SystemHeartbeatSendInterval, SystemHeartbeatReceiveInterval}).setTaskScheduler(threadPoolTaskScheduler);
        }
//        客户端向服务端发送消息地址
        registry.setApplicationDestinationPrefixes("/server/");
//        客户端订阅向指定目标推送消息的地址（点对点）;备注:SendToUser必须是enableSimpleBroker中指定的地址
//        registry.setUserDestinationPrefix("/special-user/ws/");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 可以在此处理接收到的消息
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                String simpMessageType = message.getHeaders().get("simpMessageType").toString();
                if (StompCommand.CONNECT.name().equals(simpMessageType)){
                    log.debug("Message Type: " + simpMessageType);
                    StompHeaderAccessor stompHeaderAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                    todo 权限验证
                    if(true){
//                        可以从header中获取必要信息
//                        List<String> value = stompHeaderAccessor.getNativeHeader("headerName");
                        MySimpUser simpUser = new MySimpUser();
                        simpUser.setUsername("test-name");
                        simpUser.setPassword("test-pwd");
                        simpUser.setToken("test-token");
                        simpUser.setGroupId("test-courseware-id");
                        simpUser.setSessionId(stompHeaderAccessor.getSessionId());
                        stompHeaderAccessor.setUser(simpUser);
                        return message;
                    }
                    return null;
                } else if (StompCommand.SUBSCRIBE.name().equals(simpMessageType)){
                    log.debug("Message Type: " + simpMessageType);
                } else if (SimpMessageType.HEARTBEAT.name().equals(simpMessageType)){
                    log.debug("Message Type: " + simpMessageType);
                }
                return message;
            }
        });
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        // 可以在此处理发出的消息
    }

    /**
     * 设置websocket数据大小
     * @param registration
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(1024 * 1024 * 2);
    }
}
