package com.linkjb.hcsbaihost.web;

import com.linkjb.hcsbaihost.service.MathService;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Tag(name = "测试-API")
@RestController
@RequestMapping("/mcp")
public class McpToolController {
    private final McpSyncServer mcpSyncServer;
    private final MathService mathService;

    public McpToolController(McpSyncServer mcpSyncServer, MathService mathService) {
        this.mcpSyncServer = mcpSyncServer;
        this.mathService = mathService;
    }

    @GetMapping("/add")
    public ResponseEntity<?> addTool() {
        List<McpServerFeatures.SyncToolSpecification> newTools = McpToolUtils.toSyncToolSpecifications(ToolCallbacks.from(this.mathService));
        for (McpServerFeatures.SyncToolSpecification newTool : newTools) {
            this.mcpSyncServer.addTool(newTool);
        }
        return ResponseEntity.ok("添加成功");
    }

    @GetMapping("/remove")
    public ResponseEntity<?> removeTool(String toolName) {
        this.mcpSyncServer.removeTool(toolName);
        return ResponseEntity.ok("删除工具【" + toolName + "】成功");
    }
}

