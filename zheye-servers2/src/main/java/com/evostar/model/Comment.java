package com.evostar.model;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private int id;
    private String content;
    private int userId;
    private int entityId;
    private int type;
    private Date createdDate;
    private int status;
    private int responded;

    private User user;//回答的人
    private User responder;//被回复的人
}
