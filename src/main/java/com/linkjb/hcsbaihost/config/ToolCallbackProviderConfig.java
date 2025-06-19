package com.linkjb.hcsbaihost.config;

import com.linkjb.hcsbaihost.service.StudentService;
import com.linkjb.hcsbaihost.service.TestToolService;
import com.linkjb.hcsbaihost.tool.RemoteMcpToolCallback;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.mcp.client.autoconfigure.McpToolCallbackAutoConfiguration;
import org.springframework.ai.tool.StaticToolCallbackProvider;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolCallbackProviderConfig {

    @Bean("gzhTools")
    public ToolCallbackProvider gzhRecommendTools(TestToolService testToolService) {
        return MethodToolCallbackProvider.builder().toolObjects(testToolService).build();
    }

    @Bean("studentTools")
    public ToolCallbackProvider studentTools(StudentService studentService) {
        return MethodToolCallbackProvider.builder().toolObjects(studentService).build();
    }

//    @Bean("customTools")
//    public ToolCallbackProvider customTools() {
//        return new StaticToolCallbackProvider()
//    }
}
