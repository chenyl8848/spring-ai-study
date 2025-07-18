package com.codechen.ai.chat.controller;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author：Java陈序员
 * @date：2025-7-16 17:33
 * @description：
 */
@RestController
public class DeepSeekController {

    @Autowired
    private DeepSeekChatModel deepSeekChatModel;

    @GetMapping("/deepseek1")
    public String deepSeek1(@RequestParam String message) {
        return deepSeekChatModel.call(message);
    }

    @GetMapping("/deepseek2")
    public Flux<String> deepSeek2(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return deepSeekChatModel.stream(message);
    }
}
