package com.evostar.controller;

import com.evostar.VO.CommentDataVO;
import com.evostar.VO.CommentVO;
import com.evostar.exception.ServiceException;
import com.evostar.model.Comment;
import com.evostar.model.HostHolder;
import com.evostar.model.MsgCodeEnum;
import com.evostar.service.CommentService;
import com.evostar.service.SupportService;
import com.evostar.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api(tags = "评论")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private SupportService supportService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/comment/add", method = RequestMethod.POST)
    @ApiOperation(value = "发布评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "被评论的id", defaultValue = "1",required = true),
            @ApiImplicitParam(name = "type", value = "1=回复answer，2=回复comment", required = true),
            @ApiImplicitParam(name = "content", value = "内容", defaultValue = "你好", required = true)
    })
    public HashMap<String, String> comment(@RequestBody Map<String, String> map){
        int id = Integer.parseInt(map.get("id"));
        int type = Integer.parseInt(map.get("type"));
        String content = map.get("content");
        commentService.checkExist(id, type);
        Comment comment = new Comment();
        comment.setEntityId(id);
        comment.setCreatedDate(new Date());
        comment.setUserId(hostHolder.getUser().getId());
        comment.setType(type);
        comment.setContent(content);
        if(type == 2){
            Comment comment1 = commentService.getById(id);
            comment.setResponded(comment1.getUserId());
            comment.setEntityId(comment1.getId());
        }
        //insert
        if(commentService.addComment(comment) > 0 ){
            HashMap<String, String> result =new HashMap<>();
            result.put("msg", "SUCCESS");
            return result;
        }else{
            throw new ServiceException(MsgCodeEnum.OPERATION_FAILED);
        }
    }

    //获取评论列表
    @ApiOperation(value = "获取评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "type", value = "1是answer,2是回复的评论", defaultValue = "1"),
            @ApiImplicitParam(name = "page", value = "页数", defaultValue = "1")
    })
    @RequestMapping(value = "/comment/list", method = RequestMethod.GET)
    public  CommentDataVO getCommentList(int id, int type, @RequestParam(required = false, defaultValue = "1") int page){
        return this.CommentList(id, type, page);
    }


    public CommentDataVO CommentList(int id, int type, int page){
        int limit = 10;
        int offset = (page - 1) * limit;
        List<Comment> list = commentService.getCommentListById(id, type, offset, limit);
        List<CommentVO> commentVOList = list.stream().map(comment -> {
            CommentVO commentVO = new CommentVO();
            commentVO.setContent(comment.getContent());
            commentVO.setIsDisLike(supportService.isUnSupport(comment.getId(), 3, hostHolder.getUser().getId()));
            commentVO.setIsLike(supportService.isSupport(comment.getId(), 3, hostHolder.getUser().getId()));
            commentVO.setLikeNum(supportService.supportNum(comment.getId(), 3));
            commentVO.setUser(userService.getUserVO(comment.getUser()));
            if(comment.getResponder() != null){
                commentVO.setResponder(userService.getUserVO(comment.getResponder()));
            }
            commentVO.setTime(comment.getCreatedDate());
            commentVO.setId(comment.getId());

            if(type == 1){
                CommentDataVO replies = this.getCommentList(comment.getId(), 2 ,1);
                commentVO.setReplies(replies);
            }

            return commentVO;
        }).collect(Collectors.toList());
        CommentDataVO commentDataVO = new CommentDataVO();
        commentDataVO.setData(commentVOList);
        commentDataVO.setTotalNum(commentService.getCountByTypeAndId(id, type));
        return commentDataVO;
    }
}
