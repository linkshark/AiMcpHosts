package com.linkjb.hcsbaihost.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
    @Bean
    public ChatClient chatClient(OllamaChatModel model){
        return ChatClient
                .builder(model)
//                .defaultSystem("你是一个学生信息管理助手，可以帮助用户查询学生信息。" +
//                        "你可以根据学生姓名模糊查询学生信息、根据条件分页查询学生信息。" +
//                        "回复时，请使用简洁友好的语言，并将学生信息整理为易读的格式。")
                .build();
    }
}
