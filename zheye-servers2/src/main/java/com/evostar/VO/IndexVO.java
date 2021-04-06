package com.evostar.VO;

import com.evostar.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@Api(value = "首页返回值")
public class IndexVO {
    @ApiModelProperty(value = "question的id")
    private int id;

    @ApiModelProperty(value = "question的title")
    private String title;

    @ApiModelProperty(value = "回答的用户信息")
    private User answer;

    @ApiModelProperty(value = "回答的内容的简介")
    private String summary;

    @ApiModelProperty(value = "回答的内容的完整信息")
    private AnswerVO detail;


    private ActionsVO actions;
}
