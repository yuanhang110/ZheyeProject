package com.yuanhang.controller;

import com.yuanhang.exception.ServiceException;
import com.yuanhang.model.MsgCodeEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class QuestionController {


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

        if (questionService.addQuestion(question) > 0) {
            HashMap<String, String> result = new HashMap<>();
            result.put("msg", "SUCCESS");
            return result;
        } else {
            throw new ServiceException(MsgCodeEnum.OPERATION_FAILED);
        }
    }

  
}
