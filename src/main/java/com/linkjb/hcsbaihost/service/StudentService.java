package com.linkjb.hcsbaihost.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linkjb.hcsbaihost.entity.Student;

import java.util.List;

public interface StudentService extends IService<Student> {

    List<Student> getByName(String studentName);

    List<Student> getAll();
}
