package com.codechen.ai.quickstart.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：Java陈序员
 * @date：2025-7-15 10:31
 * @description：
 */
@RestController
public class HelloController {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "message", defaultValue = "hello") String message) {
        String response = openAiChatModel.call(message);
        return response;
    }
}
