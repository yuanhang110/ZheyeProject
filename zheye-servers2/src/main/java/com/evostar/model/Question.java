package com.evostar.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

@ApiModel
@Data
@Document(indexName = "question")
public class Question {
    @ApiModelProperty(value = "问题的id")
    @Id
    private int id;
    @Field
    @ApiModelProperty(value = "问题的title")
    private String title;
    @Field
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
