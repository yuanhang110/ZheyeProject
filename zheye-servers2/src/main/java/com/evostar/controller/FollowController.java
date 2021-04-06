package com.evostar.controller;

import com.evostar.exception.ServiceException;
import com.evostar.model.HostHolder;
import com.evostar.model.MsgCodeEnum;
import com.evostar.service.FollowService;
import com.evostar.service.QuestionService;
import com.evostar.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Api(tags = "关注")
public class FollowController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private FollowService followService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;

    //关注question
    @ApiOperation(value = "关注问题")
    @ApiImplicitParam(name = "id", value = "问题的id", required = true)
    @RequestMapping(value = "/follow/question", method = RequestMethod.GET)
    public HashMap<String, String> followQuestion(int id){
        if(questionService.getById(id) == null){
            throw new ServiceException(MsgCodeEnum.DATA_NONE);
        }
        followService.follow(id, 4, hostHolder.getUser().getId());
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }

    //取消关注question
    @ApiOperation(value = "取消关注问题")
    @RequestMapping(value = "/follow/question_cancel", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id", value = "问题的id", required = true, defaultValue = "1")
    public HashMap<String, String> followCancelQuestion(int id){
        followService.followCancel(id, 4 ,hostHolder.getUser().getId());
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }


    //关注user
    @ApiOperation(value = "关注问题")
    @ApiImplicitParam(name = "id", value = "问题的id", required = true)
    @RequestMapping(value = "/follow/user", method = RequestMethod.GET)
    public HashMap<String, String> followUser(int id){
        if(userService.selectById(id) == null){
            throw new ServiceException(MsgCodeEnum.DATA_NONE);
        }
        followService.follow(id, 5, hostHolder.getUser().getId());
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }

    //取消关注user
    @ApiOperation(value = "取消关注人")
    @RequestMapping(value = "/follow/user_cancel", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id", value = "人的id", required = true, defaultValue = "1")
    public HashMap<String, String> followCancelUser(int id){
        followService.followCancel(id, 5 ,hostHolder.getUser().getId());
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }
}
