package com.evostar.netty.response;

import com.evostar.netty.Command.Command;
import com.evostar.netty.codec.Packet;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {
    private String UserId;
    private String name;
    private Boolean success;
    private String reason;

    @Override
    public Byte getCommand(){
        return Command.LOGIN_RESPONSE;
    }
}
