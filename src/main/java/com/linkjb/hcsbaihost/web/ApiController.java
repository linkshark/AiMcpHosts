package com.linkjb.hcsbaihost.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Tag(name = "测试-API")
@RestController
@RequestMapping("/api/ai")
public class ApiController {

    @Qualifier("gzhTools")
    @Autowired
    private ToolCallbackProvider gzhTools;
    @Autowired
    private  ChatClient chatClient;




    @GetMapping("/generate")
    @Operation(summary = "非流式返回", method = "POST")
    @ApiOperationSupport(order = 1)
    public String generate(@RequestParam(value = "message", defaultValue = "推荐一个公众号") String message) {
        return this.chatClient.prompt()
                .user(message)
                .toolCallbacks(gzhTools)
                .call()
                .content();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "流式返回", method = "POST")
    @ApiOperationSupport(order = 2)
    public ResponseEntity<Flux<String>> stream(@RequestParam(value = "message", defaultValue = "推荐一个公众号") String message) {
        ChatClient.StreamResponseSpec stream = this.chatClient.prompt()
                .user(message)
                .toolCallbacks(gzhTools)
                .stream();

        Flux<String> content = stream.content();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM + ";charset=UTF-8")
                .body(content);
    }


}
