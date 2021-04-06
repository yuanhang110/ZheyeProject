package com.yuanhang.controller;

import com.yuanhang.exception.ServiceException;
import com.yuanhang.model.HostHolder;
import com.yuanhang.model.MsgCodeEnum;
import com.yuanhang.model.Question;
import com.yuanhang.service.QuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class QuestionController {
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private QuestionService questionService;

    @ApiOperation("发布问题")
    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", required = true, defaultValue = "nihao", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "描述", required = true, defaultValue = "123456", dataType = "String")
    })
    public HashMap<String, String> addQuestion(@RequestBody Map<String, String> map) {
        String title = map.get("title");
        String content = map.get("content");

        Question question = new Question();
        question.setUserId(hostHolder.getUser().getId());
        question.setTitle(title);
        question.setContent(content);
        question.setCreatedDate(new Date());
        /***
         * >0说明发布成功
         */
        if (questionService.addQuestion(question) > 0) {
            HashMap<String, String> result = new HashMap<>();
            result.put("msg", "SUCCESS");
            return result;
        } else {
            throw new ServiceException(MsgCodeEnum.OPERATION_FAILED.getCode(), MsgCodeEnum.OPERATION_FAILED.getMessage());
        }
    }

    @ApiOperation("回答问题")
    @RequestMapping(value = "/answer/add", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qid", value = "问题id", required = true, defaultValue = "itn", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, defaultValue = "123456", dataType = "String")
    })
}
