package com.yuanhang.VO;

import com.yuanhang.model.User;
import lombok.Data;

@Data
public class LoginVO {
    private String msg;
    /***
     * 跳转
     */
    private String redirect;
    private User user;
}
