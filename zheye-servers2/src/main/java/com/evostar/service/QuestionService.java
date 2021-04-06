package com.evostar.service;

import com.evostar.dao.QuestionDAO;
import com.evostar.exception.ServiceException;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private SensitiveService sensitiveService;
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private EsService esService;
    public int addQuestion(Question question){
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        int res = questionDAO.addQuestion(question);
        if(res > 0){
            esService.addQuestion(question);
        }
        return res;
    }


    public void checkQid(int questionId){
        if (questionId == 0) {
            throw new ServiceException(MsgCodeEnum.PARAM_EMPTY.getCode(), "qid" + MsgCodeEnum.PARAM_EMPTY.getMessage());
        }
        //查询问题是否存在
        Question question = questionDAO.getById(questionId);
        if (question == null) {
            throw new ServiceException(MsgCodeEnum.DATA_NONE.getCode(), MsgCodeEnum.DATA_NONE.getMessage());
        }
    }

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    public Question getById(int qid){
        return questionDAO.getById(qid);
    }

    public List<Question> getLatestFollowedQuestions(List<String> userList, int offset, int limie){
        return questionDAO.selectLatestFollowedQuestions(userList, offset, limie);
    }
}
