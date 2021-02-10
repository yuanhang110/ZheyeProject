package com.yuanhang.exception;

import lombok.Data;

/***
 * lombok的@Data注解可自动生成属性的get，set方法
 */
@Data
public class ServiceException extends RuntimeException {
    /***
     * 错误码
     */
    private int code;
    /***
     * 错误信息
     */
    private String msg;


    public ServiceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
