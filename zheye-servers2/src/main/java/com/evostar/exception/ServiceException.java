package com.evostar.exception;


import com.evostar.model.MsgCodeEnum;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException{
    private int code;
    private String msg;

    public ServiceException(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(MsgCodeEnum msgCodeEnum) {
        this.code = msgCodeEnum.getCode();
        this.msg = msgCodeEnum.getMessage();
    }
}
