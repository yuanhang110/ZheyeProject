package com.evostar.netty;

import com.evostar.netty.config.NettyProperties;
import com.evostar.netty.initializer.WebSocketServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class WebSocketChatServer {
    private int port;
    private WebSocketServerInitializer webSocketServerInitializer;

    public WebSocketChatServer(WebSocketServerInitializer webSocketServerInitializer, NettyProperties nettyProperties){
        this.port = nettyProperties.getWebsocket().getPort();
        this.webSocketServerInitializer = webSocketServerInitializer;
    }

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(webSocketServerInitializer);


        serverBootstrap.bind(port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("websocket端口绑定成功，port="+port);
            }else{
                System.out.println("websocket端口绑定失败");
            }
        });
    }
}
