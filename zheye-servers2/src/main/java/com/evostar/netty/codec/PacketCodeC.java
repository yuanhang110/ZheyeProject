package com.evostar.netty.codec;


import com.alibaba.fastjson.JSON;
import com.evostar.netty.Command.Command;
import com.evostar.netty.request.LoginRequestPacket;
import com.evostar.netty.request.MessageRequestPacket;
import com.evostar.netty.response.LoginResponsePacket;
import com.evostar.netty.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据包编解码器
 * 自定义协议规则:
 * magic4字节 + 版本1字节 + 序列化算法1字节 + 指令1字节 + 数据长度4字节 + 数据内容
 */
public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
    }
    /**
     * 编码
     * 报文格式：magic4字节 + 版本1字节 + 序列化算法1字节 + 指令1字节 + 数据长度4字节 + 数据内容
     * 总字节长度 = 11 + 数据内容长度
     *
     * @param byteBuf 字节码容器
     * @param packet  Packet数据包
     * @return ByteBuf字节码
     */
    public void encode(ByteBuf byteBuf, Packet packet) {
        // 序列化 Java 对象
        byte[] bytes = JSON.toJSONBytes(packet);

        // 通讯协议规则
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(1);
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    /**
     * 解码
     * 报文格式：magic4字节 + 版本1字节 + 序列化算法1字节 + 指令1字节 + 数据长度4字节 + 数据内容
     * 总字节长度 = 11 + 数据内容长度
     *
     * @param byteBuf ByteBuf字节码
     * @return Packet数据包
     */
    public Packet decode(ByteBuf byteBuf) {
        System.out.println("解码开始-----------");
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte json = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();
        System.out.println("command = " + command);

        // 数据包长度
        int lenght = byteBuf.readInt();
        System.out.println("lenght = " + lenght);

        byte[] bytes = new byte[lenght];
        // 数据包
        byteBuf.readBytes(bytes);
        System.out.println("bytes = " + Arrays.toString(bytes));

        // 根据指令获取数据的原类型
        Class<? extends Packet> requestType = packetTypeMap.get(command);

        if (requestType != null) {
            // 将数据包反序列化成 java 对象
            Packet packet = JSON.parseObject(bytes, requestType);
            System.out.println("packet = " + packet);
            System.out.println("解码结束-----------");
            return packet;
        }
        System.out.println("解码结束-----------");
        return null;
    }

}
