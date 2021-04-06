package com.evostar;

import com.evostar.netty.WebSocketChatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private WebSocketChatServer webSocketChatServer;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        webSocketChatServer.start();
    }
}




