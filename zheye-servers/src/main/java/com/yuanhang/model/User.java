package com.yuanhang.model;

import lombok.Data;

/**
 * @author 肖远航
 */
@Data
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    /***
     * 头像
     */
    private String headUrl;
    private String token;
}
