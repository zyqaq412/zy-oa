package com.hzy.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
/**
 * @title: BaseEntity 模板实体类
 * @Author zxwyhzy
 * @Date: 2023/6/3 21:47
 * @Version 1.0
 */
@Data
public class BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableLogic //逻辑删除
    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField(exist = false) // (exist = false) 表示表里可以没有这个字段
    private Map<String,Object> param = new HashMap<>();
}

