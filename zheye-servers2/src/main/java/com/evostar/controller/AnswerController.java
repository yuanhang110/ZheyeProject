package com.evostar.controller;

import com.evostar.VO.AnswerVO;
import com.evostar.annotation.AvoidToken;
import com.evostar.exception.ServiceException;
import com.evostar.model.Answer;
import com.evostar.model.HostHolder;
import com.evostar.model.MsgCodeEnum;
import com.evostar.service.AnswerService;
import com.evostar.service.QuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AnswerController {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @ApiOperation("回答问题")
    @RequestMapping(value = "/answer/add", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qid", value = "问题id", required = true, defaultValue = "1", dataType = "itn"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, defaultValue = "123456", dataType = "String")
    })
    public HashMap<String, String> addAnser(@RequestBody Map<String, String> map){
        String qid = map.get("qid");
        String content = map.get("content");
        if(qid == null){
            throw new ServiceException(MsgCodeEnum.PARAM_EMPTY);
        }
        if(content == null){
            throw new ServiceException(MsgCodeEnum.PARAM_EMPTY);
        }
        int questionId = Integer.parseInt(qid);
        questionService.checkQid(questionId);
        Answer answer = new Answer();
        answer.setUserId(hostHolder.getUser().getId());
        answer.setContent(content);
        answer.setCreatedDate(new Date());
        answer.setQuestionId(questionId);
        if(answerService.addAnswer(answer) > 0){
            HashMap<String, String> result =new HashMap<>();
            result.put("msg", "SUCCESS");
            return result;
        }else{
            throw new ServiceException(MsgCodeEnum.OPERATION_FAILED);
        }
    }

    @AvoidToken
    @ApiOperation(value = "获取question的回答列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qid", value = "question的id", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "page", value = "分页，不填默认1", dataType = "int", defaultValue = "1")
    })
    @RequestMapping(value = "/answerList", method = {RequestMethod.GET})
    public List<AnswerVO> answerList(int qid, @RequestParam(required = false, defaultValue = "1") int page) {
        int limit = 20;
        int offset = (page - 1) * limit;
        List<Answer> answerList = answerService.getAnswerListByQidDesc(qid, offset, limit);
        return answerList.stream().map(answer -> {
            return answerService.getAnswerVO(answer);
        }).collect(Collectors.toList());
    }
}
