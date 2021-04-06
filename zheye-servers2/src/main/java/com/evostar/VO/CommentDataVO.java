package com.evostar.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommentDataVO {
    @ApiModelProperty(value = "有多少条数据")
    private int totalNum;

    private List<CommentVO> data;
}
