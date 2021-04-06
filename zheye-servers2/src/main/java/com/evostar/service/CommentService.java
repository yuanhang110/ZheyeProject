package com.evostar.service;

import com.evostar.dao.CommentDAO;
import com.evostar.exception.ServiceException;
import com.evostar.model.Comment;
import com.evostar.model.MsgCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private AnswerService answerService;

    public Comment getById(int id){
        return commentDAO.getById(id);
    }

    public void checkExist(int id, int type){
        if(type == 1){
            if(answerService.getById(id) == null){
                throw new ServiceException(MsgCodeEnum.DATA_NONE);
            }
        }else if(type == 2){
            if(this.getById(id) == null){
                throw new ServiceException(MsgCodeEnum.DATA_NONE);
            }
        }else{
            throw new ServiceException(MsgCodeEnum.PARAM_ERROR);
        }
    }

    public int addComment(Comment comment){
        return commentDAO.addComment(comment);
    }

    public List<Comment> getCommentListById(int id, int type, int offset, int limit){
        return commentDAO.getCommentListById(id, type, offset, limit);
    }

    public int getCountByTypeAndId(int id, int type){
        return commentDAO.getCountByTypeAndId(id, type);
    }

    public int getCommentCountByAnswerId(int aid){
        return commentDAO.getCommentCountByAnswerId(aid);
    }
}
