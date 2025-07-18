package com.codechen.ai.chat.controller;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author：Java陈序员
 * @date：2025-7-18 17:54
 * @description：
 */
@RestController
public class ZhiPuController {

    @Autowired
    private ZhiPuAiChatModel zhiPuAiChatModel;

    @GetMapping("/zhipu1")
    public String zhiPu1(@RequestParam String message) {
        return zhiPuAiChatModel.call(message);
    }

    @GetMapping("/zhipu2")
    public Flux<String> zhiPu2(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return zhiPuAiChatModel.stream(message);
    }
}
