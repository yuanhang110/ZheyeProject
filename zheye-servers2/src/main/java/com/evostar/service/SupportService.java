package com.evostar.service;

import com.evostar.exception.ServiceException;
import com.evostar.model.MsgCodeEnum;
import com.evostar.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SupportService {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisUtils redisUtils;

    public void checkExist(int id, int type){
        if(type == 1){
            if(questionService.getById(id) == null){
                throw new ServiceException(MsgCodeEnum.DATA_NONE);
            }
        }else if(type == 2){
            if(answerService.getById(id) == null){
                throw new ServiceException(MsgCodeEnum.DATA_NONE);
            }
        }else{
            if(commentService.getById(id) == null){
                throw new ServiceException(MsgCodeEnum.DATA_NONE);
            }
        }
    }

    //点赞
    public Boolean support(int id, int type, int userId){
        String key = entityService.getKeyByType(type);
        redisUtils.removeSetMember(key+"_UNSUPPORT_"+id, String.valueOf(userId));
        if(redisTemplate.boundSetOps(key+"_SUPPORT_"+id).isMember(String.valueOf(userId))){
            throw new ServiceException(MsgCodeEnum.OPERATION_AGAIN);
        }
        redisTemplate.boundSetOps(key+"_SUPPORT_"+id).add(String.valueOf(userId));
        return true;
    }
    //点踩
    public Boolean unSupport(int id, int type, int userId){
        String key = entityService.getKeyByType(type);
        redisUtils.removeSetMember(key+"_SUPPORT_"+id, String.valueOf(userId));
        if(redisTemplate.boundSetOps(key+"_UNSUPPORT_"+id).isMember(String.valueOf(userId))){
            throw new ServiceException(MsgCodeEnum.OPERATION_AGAIN);
        }
        redisTemplate.boundSetOps(key+"_UNSUPPORT_"+id).add(String.valueOf(userId));
        return true;
    }

    //取消赞
    public void support_cancel(int id, int type, int userId){
        String key = entityService.getKeyByType(type);
        redisUtils.removeSetMember(key+"_SUPPORT_"+id, String.valueOf(userId));
    }

    //取消踩
    public void unsupport_cancel(int id, int type, int userId){
        String key = entityService.getKeyByType(type);
        redisUtils.removeSetMember(key+"_UNSUPPORT_"+id, String.valueOf(userId));
    }

    //是否点踩
    public Boolean isUnSupport(int id, int type, int userId){
        String key = entityService.getKeyByType(type);
        return redisTemplate.boundSetOps(key+"_UNSUPPORT_"+id).isMember(String.valueOf(userId));
    }
    //是否点赞
    public Boolean isSupport(int id, int type, int userId){
        String key = entityService.getKeyByType(type);
        return redisTemplate.boundSetOps(key+"_SUPPORT_"+id).isMember(String.valueOf(userId));
    }
    //获取点赞的数量
    public int supportNum(int id, int type){
        String key = entityService.getKeyByType(type);
        return redisTemplate.boundSetOps(key+"_SUPPORT_"+id).size().intValue();
    }
    //获取踩的数量
    public int unSupportNum(int id, int type){
        String key = entityService.getKeyByType(type);
        return redisTemplate.boundSetOps(key+"_UNSUPPORT_"+id).size().intValue();
    }
}
