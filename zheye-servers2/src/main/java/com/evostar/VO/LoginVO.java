package com.evostar.VO;

import com.evostar.model.User;
import lombok.Data;


@Data
public class LoginVO {
    private String msg;
    private String redirect;
    private User user;
}
