package com.evostar.netty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Netty配置
 *
 */
@Data
@ConfigurationProperties(prefix = "netty")
public class NettyProperties {

    /**
     * 请求协议
     */
    private String protocol = "WebSocket";

    /**
     * WebSocket配置
     */
    private final Websocket websocket = new Websocket();

    /**
     * WebSocket配置
     */
    @Data
    public static class Websocket {

        /**
         * WebSocket路径
         */
        private String path = "/chat";

        /**
         * WebSocket端口
         */
        private int port = 9999;

        private final HttpObjectArrgregator httpObjectArrgregator = new HttpObjectArrgregator();

        @Data
        public static class HttpObjectArrgregator {

            /**
             * 最大内容长度
             */
            private int maxContentLength = 64 * 1024;
        }
    }
}

