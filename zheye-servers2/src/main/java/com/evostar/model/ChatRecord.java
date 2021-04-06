package com.evostar.model;

import lombok.Data;

@Data
public class ChatRecord {
    private int id;
    private int fromUserId;
    private int toUserId;
    private String content;
    private int time;
}
