package com.it.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @author yiming@micous.com
 * @date 2024/6/7 10:42
 */
@Slf4j
public class MyWebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("[MyWebSocketInterceptor#BeforeHandshake] Request from " + request.getRemoteAddress().getHostString());
        if (request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            String token = serverHttpRequest.getServletRequest().getHeader("token");
            //这里做一个简单的鉴权，只有符合条件的鉴权才能握手成功
            if(StringUtils.isEmpty(token)){
                token = serverHttpRequest.getServletRequest().getParameter("token");
            }

            attributes.put("uid", token);
            log.info("http request {}",token);

        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        log.info("[MyWebSocketInterceptor#afterHandshake] Request from " + request.getRemoteAddress().getHostString());
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
