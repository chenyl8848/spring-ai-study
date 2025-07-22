package com.codechen.ai.chatclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：Java陈序员
 * @date：2025-7-22 11:33
 * @description：
 */
@RestController
public class MultiChatModelController {

    @Autowired
    private ChatClient openAiChatClient;

    @Autowired
    private ChatClient deepSeekChatClient;

    @Autowired
    private OpenAiChatModel baseChatModel;

    @GetMapping("/multi1")
    public String multiChatModel1(@RequestParam(required = false, defaultValue = "openAi") String modelType,
                                 String message) {

        if (modelType.equalsIgnoreCase("deepSeek")) {
            return deepSeekChatClient.prompt()
                    .user(message)
                    .call()
                    .content();
        } else {
            return openAiChatClient.prompt()
                    .user(message)
                    .call()
                    .content();
        }
    }

    @GetMapping("/multi2")
    public String multiChatModel2(@RequestParam(required = false, defaultValue = "openAi") String modelType,
                                 String message) {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl("https://api.openai.com/")
                .apiKey(System.getenv("OPENAI_SERVICE_API_KEY"))
                .build();

        OpenAiChatModel openAiChatModel = baseChatModel.mutate()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("")
                        .temperature(0.8)
                        .build()
                ).build();

        OpenAiApi deepSeekApi = OpenAiApi.builder()
                .baseUrl("https://api.openai.com/")
                .apiKey(System.getenv("OPENAI_SERVICE_API_KEY"))
                .build();

        OpenAiChatModel deepSeekChatModel = baseChatModel.mutate()
                .openAiApi(deepSeekApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("")
                        .temperature(0.8)
                        .build())
                .build();

        ChatClient openAiChatClient2 = ChatClient.create(openAiChatModel);
        ChatClient deepSeekChatClient2 = ChatClient.create(deepSeekChatModel);

        if (modelType.equalsIgnoreCase("deepSeek")) {
           return deepSeekChatClient2.prompt()
                    .user(message)
                    .call()
                    .content();
        } else {
            return openAiChatClient2.prompt()
                    .user(message)
                    .call()
                    .content();
        }
    }

}
