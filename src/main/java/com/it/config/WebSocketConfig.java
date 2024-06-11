package com.it.config;

import com.it.websocket.ClientWebSocketHandler;
import com.it.websocket.MyWebSocketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author yiming@micous.com
 * @date 2024/6/7 16:36
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public ServerEndpointExporter  endpointExporter(){
        return new ServerEndpointExporter();
    }

    @Bean
    public MyWebSocketInterceptor socketInterceptor(){
        return new MyWebSocketInterceptor();
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ClientWebSocketHandler(), "/websocket").setAllowedOrigins("*").addInterceptors(socketInterceptor());
    }
}
