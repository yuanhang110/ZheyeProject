package com.evostar.VO;

import lombok.Data;

@Data
public class ChatRecordVO {
    private int id;
    private UserVO fromUser;
    private UserVO toUser;
    private String content;
    private int time;
}
