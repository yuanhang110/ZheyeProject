package com.yuanhang.service;

import com.yuanhang.dao.QuestionDAO;
import com.yuanhang.exception.ServiceException;
import com.yuanhang.model.MsgCodeEnum;
import com.yuanhang.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

/**
 * @author 肖远航
 */
@Service
public class QuestionService {
    @Autowired
    private SensitiveService sensitiveService;

    @Autowired
    private QuestionDAO questionDAO;

    public int addQuestion(Question question) {

        /***
         * htmlescape,让问题的标题和内容可以正常显示，不被识别为html标签而被转义
         */
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));

        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDAO.addQuestion(question);
    }

    public void checkQid(int questionId) {
        if (questionId == 0) {
            throw new ServiceException(MsgCodeEnum.PARAM_EMPTY.getCode(), "qid" + MsgCodeEnum.PARAM_EMPTY.getMessage());
        }
        //查询问题是否存在
        Question question = questionDAO.getById(questionId);
        if (question == null) {
            throw new ServiceException(MsgCodeEnum.DATA_NONE.getCode(), MsgCodeEnum.DATA_NONE.getMessage());
        }
    }


}
