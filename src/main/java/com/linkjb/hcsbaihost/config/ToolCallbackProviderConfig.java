package com.linkjb.hcsbaihost.config;

import com.linkjb.hcsbaihost.service.StudentService;
import com.linkjb.hcsbaihost.service.ToolService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolCallbackProviderConfig {

    @Bean("gzhTools")
    public ToolCallbackProvider gzhRecommendTools(ToolService toolService) {
        return MethodToolCallbackProvider.builder().toolObjects(toolService).build();
    }

    @Bean("studentTools")
    public ToolCallbackProvider studentTools(StudentService studentService) {
        return MethodToolCallbackProvider.builder().toolObjects(studentService).build();
    }
}
