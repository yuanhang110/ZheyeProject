package com.evostar.netty.initializer;

import com.evostar.netty.codec.WebSocketPacketCodec;
import com.evostar.netty.config.NettyProperties;
import com.evostar.netty.handler.LoginRequestHanlder;
import com.evostar.netty.handler.MessageRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

@Component
public class WebSocketServerInitializer extends ChannelInitializer<NioSocketChannel> {
    private String websocketPath;
    private int maxContentLength;

    public WebSocketServerInitializer(NettyProperties nettyProperties){
        this.websocketPath = nettyProperties.getWebsocket().getPath();
        this.maxContentLength = nettyProperties.getWebsocket().getHttpObjectArrgregator().getMaxContentLength();
    }

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //处理第一连接http的握手请求
        pipeline.addLast((new HttpServerCodec()));
        //写文件内容
        pipeline.addLast(new ChunkedWriteHandler());
        //保证http请求完整性
        pipeline.addLast(new HttpObjectAggregator(maxContentLength));
        //处理其他的websocketFrame
        pipeline.addLast(new WebSocketServerProtocolHandler(websocketPath));

        //websocket数据包的编码、解码
        pipeline.addLast(WebSocketPacketCodec.INSTANCE);

        pipeline.addLast(LoginRequestHanlder.INSTANCE);
        pipeline.addLast(MessageRequestHandler.INSTANCE);


    }
}
