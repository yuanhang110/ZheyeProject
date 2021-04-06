package com.evostar.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class Answer {
    @ApiModelProperty(value = "answer的id")
    private int id;
    @ApiModelProperty(value = "回答者的id")
    private int userId;
    @ApiModelProperty(value = "问题的id")
    private int questionId;
    @ApiModelProperty(value = "回答的时间")
    private Date createdDate;
    @ApiModelProperty(value = "回复数量")
    private int reviewNum;
    @ApiModelProperty(value = "回答的内容")
    private String content;
    @ApiModelProperty(value = "评论数量")
    private int commentCount;
    private int supportCount;
    private int unsupportCount;
    @ApiModelProperty(value = "回答者本人的信息")
    private User answer;
}
