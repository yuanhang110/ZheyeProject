package com.yuanhang.exception;

import com.yuanhang.model.MsgCodeEnum;

public class UnauthorizedException extends ServiceException {
    public UnauthorizedException() {
        super(MsgCodeEnum.UNAUTHORIZED_ERROR);
    }
}
