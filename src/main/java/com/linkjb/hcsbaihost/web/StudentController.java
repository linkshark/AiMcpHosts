package com.linkjb.hcsbaihost.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.linkjb.hcsbaihost.entity.Student;
import com.linkjb.hcsbaihost.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "学生")
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;
    @Resource
    private ChatClient studentChatClient;
    @Qualifier("studentTools")
    @Resource
    ToolCallbackProvider studentToolCallbackProvider;

    @GetMapping("/getByName")
    List<Student> getByName(@RequestParam(value = "studentName") String studentName){
        return studentService.getByName(studentName);
    }


    @GetMapping("/chat")
    @Operation(summary = "非流式返回", method = "POST")
    @ApiOperationSupport(order = 1)
    public String generate(@RequestParam(value = "message") String message) {
        return this.studentChatClient.prompt()
                .user(message)
                .toolCallbacks(studentToolCallbackProvider)
                .call()
                .content();
    }
}
