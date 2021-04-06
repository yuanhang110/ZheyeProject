package com.evostar.exception;

import com.evostar.model.MsgCodeEnum;

public class UnauthorizedException extends ServiceException {
    public UnauthorizedException() {
        super(MsgCodeEnum.UNAUTHORIZED_ERROR);
    }
}
