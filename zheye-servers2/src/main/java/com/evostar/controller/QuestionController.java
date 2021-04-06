package com.evostar.controller;

import com.evostar.VO.AnswerVO;
import com.evostar.VO.QuestionDetailVO;
import com.evostar.annotation.AvoidToken;
import com.evostar.exception.ServiceException;
import com.evostar.model.Answer;
import com.evostar.model.HostHolder;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.Question;
import com.evostar.service.AnswerService;
import com.evostar.service.FollowService;
import com.evostar.service.QuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class QuestionController {
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private FollowService followService;

    @Autowired
    private AnswerService answerService;
    @ApiOperation("发布问题")
    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", required = true, defaultValue = "nihao", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "描述", required = true, defaultValue = "123456", dataType = "String")
    })
    public HashMap<String, String> addQuestion(@RequestBody Map<String, String> map){
        String title = map.get("title");
        String content = map.get("content");

        Question question = new Question();
        question.setUserId(hostHolder.getUser().getId());
        question.setTitle(title);
        question.setContent(content);
        question.setCreatedDate(new Date());

        if(questionService.addQuestion(question) > 0){
            HashMap<String, String> result =new HashMap<>();
            result.put("msg", "SUCCESS");
            return result;
        }else{
            throw new ServiceException(MsgCodeEnum.OPERATION_FAILED);
        }
    }

    //问题详情
    @AvoidToken
    @ApiOperation(value = "问题详情")
    @ApiImplicitParam(name = "qid", value = "question的id", dataType = "int", defaultValue = "1", required = true)
    @RequestMapping(value = "/question/detail", method = {RequestMethod.GET})
    public QuestionDetailVO QuestionDetail(int qid){
        Question question = questionService.getById(qid);
        if(question == null){
            throw new ServiceException(MsgCodeEnum.DATA_NONE);
        }
        List<Answer> answerList = answerService.getAnswerListByQidDesc(qid, 0, 20);
        List<AnswerVO> answerVOList = answerList.stream().map(answer -> {
            return answerService.getAnswerVO(answer);
        }).collect(Collectors.toList());

        QuestionDetailVO detailVO = new QuestionDetailVO();
        detailVO.setAnswerList(answerVOList);
        detailVO.setId(question.getId());
        detailVO.setAnswerNum(answerService.getAnserCountByQid(qid));
        detailVO.setDetail(question.getContent());
        detailVO.setLookNum(question.getLookNum());
        detailVO.setTitle(question.getTitle());
        if(hostHolder.getUser() != null){
            detailVO.setIsFollow(followService.isFollowQuestion(question.getId(), hostHolder.getUser().getId()));
        }
        detailVO.setFollowNum(followService.followQuestionNum(question.getId()));
        String summary = question.getContent() != null && question.getContent().length() > 50 ? question.getContent().substring(0, 50) + "......" : question.getContent();
        detailVO.setSummary(summary);
        return detailVO;
    }
}
