package com.hzy.vo.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: AssginRoleVo
 * @Author zxwyhzy
 * @Date: 2023/6/14 16:01
 * @Version 1.0
 */
@ApiModel(description = "分配菜单")
@Data
public class AssginRoleVo {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "角色id列表")
    private List<Long> roleIdList;

}

