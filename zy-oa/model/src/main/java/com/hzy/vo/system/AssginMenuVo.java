package com.hzy.vo.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: AssginMenuVo
 * @Author zxwyhzy
 * @Date: 2023/6/14 20:31
 * @Version 1.0
 */
@ApiModel(description = "分配菜单")
@Data
public class AssginMenuVo {

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "菜单id列表")
    private List<Long> menuIdList;

}
