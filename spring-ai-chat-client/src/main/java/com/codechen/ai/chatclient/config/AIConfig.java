package com.codechen.ai.chatclient.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author：Java陈序员
 * @date：2025-7-18 17:46
 * @description：
 */
@Configuration
public class AIConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder clientBuilder) {
        return clientBuilder.build();
    }
}
