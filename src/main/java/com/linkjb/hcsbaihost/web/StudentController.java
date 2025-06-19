package com.linkjb.hcsbaihost.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.linkjb.hcsbaihost.entity.McpServer;
import com.linkjb.hcsbaihost.entity.Student;
import com.linkjb.hcsbaihost.entity.Tool;
import com.linkjb.hcsbaihost.entity.ToolParam;
import com.linkjb.hcsbaihost.service.StudentService;
import com.linkjb.hcsbaihost.service.ToolParamService;
import com.linkjb.hcsbaihost.service.ToolService;
import com.linkjb.hcsbaihost.service.impl.McpServerServiceImpl;
import com.linkjb.hcsbaihost.tool.RemoteMcpToolCallback;
import com.linkjb.hcsbaihost.tool.RemoteMcpUtil;
import com.linkjb.hcsbaihost.vo.ToolParamVO;
import com.linkjb.hcsbaihost.vo.ToolVO;
import io.modelcontextprotocol.server.McpAsyncServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.WebFluxSseServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.linkjb.hcsbaihost.service.impl.McpServerServiceImpl.*;
import static com.linkjb.hcsbaihost.service.impl.McpServerServiceImpl.toolSpecificationMap;
@Slf4j
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
    @Resource
    private McpSyncServer mcpSyncServer;
    @Resource
    private ChatClient chatClient;
    @Resource
    private OllamaChatModel model;
    @Resource
    McpServerServiceImpl mcpServerService;
    @Resource
    ToolService toolService;
    @Resource
    ToolParamService toolParamService;

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

    @GetMapping("/chatAll")
    @Operation(summary = "非流式返回", method = "POST")
    @ApiOperationSupport(order = 1)
    public String chatAll(@RequestParam(value = "message",defaultValue = "ss") String message) {
        ChatClient cli = ChatClient.builder(model).defaultSystem("你是一个机器人 请简洁回答问题").build();

        List<McpServer> list = mcpServerService.list(Wrappers.lambdaQuery(McpServer.class));
        if (CollectionUtil.isNotEmpty(list)) {
            List<Long> ids = list.stream().map(McpServer::getId).toList();
            Map<Long, List<Tool>> toolMap = toolService.list(Wrappers.lambdaQuery(Tool.class)
                    .in(Tool::getServerId, ids)).stream().collect(Collectors.groupingBy(Tool::getServerId));
            for (McpServer server : list) {
                List<Tool> tools = toolMap.get(server.getId());
                List<ToolParam> paramList = toolParamService.list(Wrappers.lambdaQuery(ToolParam.class)
                        .in(ToolParam::getToolId, tools.stream().map(Tool::getId).toList()));
                List<ToolVO> toolVOS = BeanUtil.copyToList(tools, ToolVO.class);
                if (CollectionUtil.isNotEmpty(paramList)) {
                    Map<Long, List<ToolParam>> paramMap = paramList.stream().collect(Collectors.groupingBy(ToolParam::getToolId));
                    for (ToolVO toolVO : toolVOS) {
                        toolVO.setToolParam(BeanUtil.copyToList(paramMap.get(toolVO.getId()), ToolParamVO.class));
                    }
                }
                List<ToolCallback> remoteMcpToolCallbacks = RemoteMcpUtil.convertNormalToolCallback(toolVOS);
                return cli.prompt().toolCallbacks(remoteMcpToolCallbacks).user(message)
                        //.toolCallbacks(studentToolCallbackProvider)
                        .call()
                        .content();
            }
        }

        return "";

    }
}
