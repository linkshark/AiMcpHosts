package com.linkjb.hcsbaihost.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 工具参数信息
 * @TableName tool_param
 */
@TableName(value = "tool_param")
@Data
public class ToolParam {
    /**
      id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
      工具id
     */
    private Long toolId;

    /**
      名称
     */
    private String name;

    /**
      描述
     */
    private String description;

    /**
     是否必需 0否 1是
     */
    private Integer required;

    /**
      创建时间
     */
    private String createTime;

    /**
      更新时间
     */
    private String updateTime;

    /**
      逻辑删除 0未删除 1已删除
     */
    @TableLogic
    private Integer isDeleted;
}