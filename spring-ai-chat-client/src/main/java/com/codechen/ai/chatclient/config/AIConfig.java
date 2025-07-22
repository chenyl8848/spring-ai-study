package com.codechen.ai.chatclient.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author：Java陈序员
 * @date：2025-7-18 17:46
 * @description：
 */
@Configuration
public class AIConfig {

//    @Bean
//    public ChatClient chatClient(ChatClient.Builder clientBuilder) {
//        return clientBuilder.build();
//    }

    @Bean
    public ChatClient chatClient(ChatModel openAiChatModel) {
//        return ChatClient.create(openAiChatModel);

        return ChatClient.create(openAiChatModel)
                .mutate()
//                .defaultSystem("你是一个前端开发工程师，精通前端开发技术栈")
                .defaultSystem("你是一个前端开发工程师，精通 {language}")
                .build();
    }

    @Bean
    public ChatClient openAiChatClient(ChatModel openAiChatModel) {
        return ChatClient.create(openAiChatModel);
    }

    @Bean
    public ChatClient deepSeekChatClient(ChatModel deepSeekChatModel) {
        return ChatClient.create(deepSeekChatModel);
    }
}
