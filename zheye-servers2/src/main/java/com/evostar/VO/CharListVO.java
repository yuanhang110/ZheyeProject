package com.evostar.VO;

import lombok.Data;

@Data
public class CharListVO {
    private UserVO user;
    private String content;
    private int time;
    private int unreadNum;
}
