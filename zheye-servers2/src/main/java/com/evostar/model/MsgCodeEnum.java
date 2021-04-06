package com.evostar.model;

public enum MsgCodeEnum implements ErrorCode {
    UNAUTHORIZED_ERROR(100, "未登录或登录已失效"),

    PASSWORD_ERROR(300, "密码错误"),
    ACCOUNT_ERROR(301, "账号不存在"),
    ACCOUNT_EMPTY(302, "账号不能为空"),
    PASSWORD_EMPTY(303, "密码不能为空"),
    ACCOUNT_REGISTERED(304, "账号已被注册"),
    REGISTERED_FAILED(305, "注册失败"),
    DATA_NONE(306, "数据不存在"),
    OPERATION_FAILED(307, "操作失败"),
    PARAM_EMPTY(308, "参数不能为空"),
    PARAM_ERROR(309, "参数错误"),
    OPERATION_AGAIN(310, "请勿重复操作");

    private int code;
    private String message;

    MsgCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
