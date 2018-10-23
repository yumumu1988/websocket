package com.yumumu.websocket.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WebSocketClusterByRedisCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String enableWsCluster = conditionContext.getEnvironment().getProperty("ws.enable.cluster");
        String stompBrokerOn = conditionContext.getEnvironment().getProperty("ws.enable.cluster.stomp.broker.on");
        return Boolean.valueOf(enableWsCluster) && !Boolean.valueOf(stompBrokerOn);
    }

}
