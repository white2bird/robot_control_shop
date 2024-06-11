package com.it.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yiming@micous.com
 * @date 2024/6/6 17:46
 */
@Slf4j
public class ClientWebSocketHandler extends AbstractWebSocketHandler {

    private static final Map<String, WebSocketBean> webSocketBeanMap;

    private static final AtomicInteger clientIdMaker;

    static {
        webSocketBeanMap = new ConcurrentHashMap<>();
        clientIdMaker = new AtomicInteger(0);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        /***
         * 当链接建立之后
         */
        WebSocketBean webSocketBean = new WebSocketBean();
        webSocketBean.setWebSocketSession(session);
        webSocketBean.setClientId(clientIdMaker.getAndIncrement());
        webSocketBeanMap.put(session.getId(), webSocketBean);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketBeanMap.remove(session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
        if(session.isOpen()){
            session.close();
        }
        webSocketBeanMap.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //处理接收到的消息
        Object uid = session.getAttributes().get("uid");
        log.info("Received message from client[ID:" + webSocketBeanMap.get(session.getId()).getClientId() +
                "]; Content is [" + message.getPayload() + "].");

        TextMessage textMessage = new TextMessage("send:Uid ["+ uid.toString() + "]  msg: " +message.getPayload());
        Collection<WebSocketBean> values = webSocketBeanMap.values();
        for(WebSocketBean socketBean : values){
            socketBean.getWebSocketSession().sendMessage(textMessage);
        }
    }
}
