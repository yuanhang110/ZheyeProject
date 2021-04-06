package com.evostar.model;

import lombok.Data;

public enum EntityType {
    ENTITY_QUESTION(1, "QUESTION"),//跟问题做互动
    ENTITY_ANSWER(2, "ANSWER"),//跟回答的互动
    ENTITY_COMMENT(3, "COMMENT"),//跟评论互动
    ENTITY_FOLLOWED_QUESTION(4, "FOLLOWED_QUESTION_"),//关注问题,以问题为中心
    ENTITY_FOLLOWED_USER(5, "FOLLOWED_USER_"),//关注人，以被关注的人为中心
    ENTITY_FOLLOW_QUESTIONS(6,"FOLLOW_QUESTIONS_"),//关注问题,以我为中心
    ENTITY_FOLLOW_USERS(7,"FOLLOW_USERS_"),//关注人，以我为中心，我关注了哪些人
    ;//跟评论的互动
    int type;
    String key;

    public void setType(int type) {
        this.type = type;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public int getType(){
        return type;
    }
    public String getKey(){
        return key;
    }
    EntityType(int type, String key){
        this.type = type;
        this.key = key;
    }
}
