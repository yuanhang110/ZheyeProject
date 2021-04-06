package com.evostar.netty.request;

import com.evostar.netty.Command.Command;
import com.evostar.netty.codec.Packet;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {
    private int toUserId;
    private String content;
    private int time;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
