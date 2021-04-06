package com.evostar.netty.request;

import com.evostar.netty.Command.Command;
import com.evostar.netty.codec.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    @Override
    public Byte getCommand(){
        return Command.LOGIN_REQUEST;
    }
}
