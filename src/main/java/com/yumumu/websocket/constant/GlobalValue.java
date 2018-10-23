package com.yumumu.websocket.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalValue {
    public static String WEB_SOCKET_ALLOWED_ORIGINS;
    public static Boolean WEB_SOCKET_CLUSTER_ON;
    public static Boolean WEB_SOCKET_CLUSTER_STOMP_BROKER_ON;
    public static String WEB_SOCKET_CLUSTER_STOMP_BROKER_HOST;
    public static int WEB_SOCKET_CLUSTER_STOMP_BROKER_PORT;
    public static String WEB_SOCKET_CLUSTER_STOMP_BROKER_SYS_LOGIN;
    public static String WEB_SOCKET_CLUSTER_STOMP_BROKER_SYS_PWD;
    public static String WEB_SOCKET_CLUSTER_STOMP_BROKER_CLI_LOGIN;
    public static String WEB_SOCKET_CLUSTER_STOMP_BROKER_CLI_PWD;

    public static final String WEB_SOCKET_CHANNEL = "ws:shared-channel";

    @Value("${ws.allowed.origins}")
    public void setWebSocketAllowedOrigins(String webSocketAllowedOrigins) {
        WEB_SOCKET_ALLOWED_ORIGINS = webSocketAllowedOrigins;
    }

    @Value("${ws.enable.cluster}")
    public void setWebSocketClusterOn(Boolean webSocketClusterOn) {
        WEB_SOCKET_CLUSTER_ON = webSocketClusterOn;
    }

    @Value("${ws.enable.cluster.stomp.broker.on}")
    public void setWebSocketClusterStompBrokerOn(Boolean webSocketClusterStompBrokerOn) {
        WEB_SOCKET_CLUSTER_STOMP_BROKER_ON = webSocketClusterStompBrokerOn;
    }

    @Value("${ws.cluster.stomp.broker.host}")
    public void setWebSocketClusterStompBrokerHost(String webSocketClusterStompBrokerHost) {
        WEB_SOCKET_CLUSTER_STOMP_BROKER_HOST = webSocketClusterStompBrokerHost;
    }

    @Value("${ws.cluster.stomp.broker.port}")
    public void setWebSocketClusterStompBrokerPort(int webSocketClusterStompBrokerPort) {
        WEB_SOCKET_CLUSTER_STOMP_BROKER_PORT = webSocketClusterStompBrokerPort;
    }

    @Value("${ws.cluster.stomp.broker.sys.login}")
    public void setWebSocketClusterStompBrokerSysLogin(String webSocketClusterStompBrokerSysLogin) {
        WEB_SOCKET_CLUSTER_STOMP_BROKER_SYS_LOGIN = webSocketClusterStompBrokerSysLogin;
    }

    @Value("${ws.cluster.stomp.broker.sys.pwd}")
    public void setWebSocketClusterStompBrokerSysPwd(String webSocketClusterStompBrokerSysPwd) {
        WEB_SOCKET_CLUSTER_STOMP_BROKER_SYS_PWD = webSocketClusterStompBrokerSysPwd;
    }

    @Value("${ws.cluster.stomp.broker.cli.login}")
    public void setWebSocketClusterStompBrokerCliLogin(String webSocketClusterStompBrokerCliLogin) {
        WEB_SOCKET_CLUSTER_STOMP_BROKER_CLI_LOGIN = webSocketClusterStompBrokerCliLogin;
    }

    @Value("${ws.cluster.stomp.broker.cli.pwd}")
    public void setWebSocketClusterStompBrokerCliPwd(String webSocketClusterStompBrokerCliPwd) {
        WEB_SOCKET_CLUSTER_STOMP_BROKER_CLI_PWD = webSocketClusterStompBrokerCliPwd;
    }
}
