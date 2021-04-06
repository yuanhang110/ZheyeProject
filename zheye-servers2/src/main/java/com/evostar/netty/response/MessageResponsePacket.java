package com.evostar.netty.response;

import com.evostar.VO.UserVO;
import com.evostar.netty.Command.Command;
import com.evostar.netty.codec.Packet;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {

    private UserVO fromUser;
    private UserVO toUser;
    private String content;
    private int time;

    private int unreadNum;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
