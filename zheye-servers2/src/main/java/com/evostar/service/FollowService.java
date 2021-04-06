package com.evostar.service;

import com.evostar.exception.ServiceException;
import com.evostar.model.MsgCodeEnum;
import com.evostar.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {
    @Autowired
    private EntityService entityService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisUtils redisUtils;


    //关注
    public void follow(int beFollowedId, int type, int followUserId){
        String key = entityService.getKeyByType(type);
        if(redisTemplate.boundSetOps(key+beFollowedId).isMember(String.valueOf(followUserId))){
            throw new ServiceException(MsgCodeEnum.OPERATION_AGAIN);
        }
        //以问题/被关注人 为中心，
        redisTemplate.boundSetOps(key+beFollowedId).add(String.valueOf(followUserId));
        //以我为中心，记录数据
        key = entityService.getKeyByType(type + 2);
        redisTemplate.boundSetOps(key+followUserId).add(String.valueOf(beFollowedId));
    }
    //取消关注
    public void followCancel(int beFollowedId, int type, int followUserId){
        String key = entityService.getKeyByType(type);
        redisUtils.removeSetMember(key+beFollowedId, String.valueOf(followUserId));
        key = entityService.getKeyByType(type + 2);
        redisUtils.removeSetMember(key+followUserId, String.valueOf(beFollowedId));
    }

    //获取所有我关注的人
    public List<String> getFollowedUsers(int userId){
        String key = entityService.getKeyByType(7);
        Set<String> users = redisTemplate.boundSetOps(key+userId).members();
        return new ArrayList<>(users);
    }
    //获取关注我的人
    public List<String> getFollowUsers(int userId){
        String key = entityService.getKeyByType(5);
        Set<String> users = redisTemplate.boundSetOps(key+userId).members();
        return new ArrayList<>(users);
    }
    //followUserId 是否关注 beFollowUserId
    public Boolean isFollowUser(int beFollowUserId, int followUserId){
        String key = entityService.getKeyByType(5);
        return redisTemplate.boundSetOps(key+beFollowUserId).isMember(String.valueOf(followUserId));
    }
    //followUserId 是否关注 beFollowQuestionId
    public Boolean isFollowQuestion(int beFollowQeestionId, int followUserId){
        String key = entityService.getKeyByType(4);
        return redisTemplate.boundSetOps(key+beFollowQeestionId).isMember(String.valueOf(followUserId));
    }
    //有多少人关注
    public int followUserNum(int beFollowUserId){
        String key = entityService.getKeyByType(5);
        return redisTemplate.boundSetOps(key+beFollowUserId).size().intValue();
    }

    //有多少人关注问题
    public int followQuestionNum(int beFollowUserId){
        String key = entityService.getKeyByType(4);
        return redisTemplate.boundSetOps(key+beFollowUserId).size().intValue();
    }
}
