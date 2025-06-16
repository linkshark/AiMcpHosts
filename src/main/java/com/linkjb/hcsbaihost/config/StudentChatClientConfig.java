package com.linkjb.hcsbaihost.config;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentChatClientConfig {
    @Resource
    private ChatMemory chatMemory;
    @Bean
    public ChatClient studentChatClient(OllamaChatModel model){
        return ChatClient
                .builder(model)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultSystem("你是一个学生信息管理助手，可以帮助用户查询学生信息。" +
                        "你可以根据学生姓名模糊查询学生信息、根据条件分页查询学生信息。" +
                        "回复时，请使用简洁友好的语言，并将学生信息整理为易读的格式。")
                .build();
    }
}
