package com.linkjb.hcsbaihost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linkjb.hcsbaihost.dao.StudentMapper;
import com.linkjb.hcsbaihost.entity.Student;
import com.linkjb.hcsbaihost.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Tool(name = "getByName", description = "根据学生姓名模糊查询学生成绩信息")
    @Override
    public List<Student> getByName(@ToolParam(description = "姓名") String studentName) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Student::getStudentName, studentName);
        return this.list(queryWrapper);
    }

    @Override
    @Tool(name = "getAll", description = "获取所有学生的成绩信息")
    public List<Student> getAll() {
        return this.list();
    }
}
