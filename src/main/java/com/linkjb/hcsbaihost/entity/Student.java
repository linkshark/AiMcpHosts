package com.linkjb.hcsbaihost.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
//@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("student")
@Schema(title = "学生类")
public class Student {
    @TableId(type = IdType.AUTO)
    private Integer incrementId;
    @TableField("student_name")
    @Schema(title = "姓名")
    private String studentName;
    @TableField("grade")
    @Schema(title = "成绩")
    private Integer grade;
    @TableField("subject")
    @Schema(title = "考试科目")
    private String subject;
    @TableField(value = "test_date")
    @Schema(title = "考试日期")
    private Date testDate;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(title = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date createTime;
    @TableField(value = "update_time")
    @Schema(title = "更新时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date updateTime;
}
