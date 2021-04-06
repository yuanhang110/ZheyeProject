package com.evostar.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserVO {
    @ApiModelProperty(value = "用户id")
    private int id;
    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "用户的头像")
    private String headUrl;

    @ApiModelProperty(value = "是否关注")
    private Boolean isFollow = false;

    @ApiModelProperty(value = "回答问题的数量")
    private int answerNum;

    @ApiModelProperty(value = "关注我的人的数量")
    private int followNum;
}
