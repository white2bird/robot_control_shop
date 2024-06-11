package com.it.websocket;

import org.springframework.web.socket.WebSocketSession;

/**
 * @author yiming@micous.com
 * @date 2024/6/7 10:26
 */
public class WebSocketBean {

    private int clientId;

    private WebSocketSession webSocketSession;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }
}
