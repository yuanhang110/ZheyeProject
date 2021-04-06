package com.evostar.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActionsVO {
    @ApiModelProperty(value = "浏览的数量")
    private int reviewNum;

    @ApiModelProperty(value = "是否点赞")
    private Boolean isAgree;

    @ApiModelProperty(value = "点赞的数量")
    private int agreeNum;

    @ApiModelProperty(value = "是否踩了")
    private boolean isDisagree;

    @ApiModelProperty(value = "是否喜欢")
    private boolean isLike;

    @ApiModelProperty(value = "是否收藏")
    private boolean isCollect;
}
