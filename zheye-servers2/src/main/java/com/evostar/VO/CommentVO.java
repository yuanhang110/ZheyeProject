package com.evostar.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CommentVO {
    @ApiModelProperty(value = "评论的id")
    private int id;
    @ApiModelProperty(value = "回复的人")
    private UserVO user;

    @ApiModelProperty(value = "被回复的人")
    private UserVO responder;

    @ApiModelProperty(value = "时间")
    private Date time;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "点赞的数量")
    private int likeNum;

    @ApiModelProperty(value = "是否点赞")
    private Boolean isLike;

    @ApiModelProperty(value = "是否点踩")
    private Boolean isDisLike;

    @ApiModelProperty(value = "回复的数据")
    private CommentDataVO replies;
}
