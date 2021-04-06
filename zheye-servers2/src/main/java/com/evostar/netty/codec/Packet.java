package com.evostar.netty.codec;

import lombok.Data;

/**
 * 数据包抽象基类
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 获取指令
     */
    public abstract Byte getCommand();

}
