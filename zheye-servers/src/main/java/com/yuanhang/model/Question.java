package com.yuanhang.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class Question {
    @ApiModelProperty(value = "问题的id")
    private int id;
    @ApiModelProperty(value = "问题的title")
    private String title;
    @ApiModelProperty(value = "问题的描述")
    private String content;
    @ApiModelProperty(value = "创建问题的时间")
    private Date createdDate;
    @ApiModelProperty(value = "创建问题的userId")
    private int userId;
    @ApiModelProperty(value = "评论的数量")
    private int commentCount;
    @ApiModelProperty(value = "点赞的数量")
    private int supportCount;
    @ApiModelProperty(value = "踩的数量")
    private int unSupportCount;
    @ApiModelProperty(value = "浏览量")
    private int lookNum;
}
