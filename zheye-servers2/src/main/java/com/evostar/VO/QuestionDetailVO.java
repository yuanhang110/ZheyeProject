package com.evostar.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDetailVO {
    @ApiModelProperty(value = "问题id")
    private int id;
    @ApiModelProperty(value = "问题的title")
    private String title;
    @ApiModelProperty(value = "问题的简述")
    private String summary;
    @ApiModelProperty(value = "问题的完整描述")
    private String detail;
    @ApiModelProperty(value = "浏览量")
    private int lookNum;
    @ApiModelProperty(value = "回答的数据")
    private List<AnswerVO> answerList;
    @ApiModelProperty(value = "回答的数量")
    private int answerNum;

    @ApiModelProperty(value = "关注量")
    private int followNum;

    @ApiModelProperty(value = "是否关注了该问题")
    private Boolean isFollow;
}
