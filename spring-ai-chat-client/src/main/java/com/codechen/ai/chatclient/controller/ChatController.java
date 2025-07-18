package com.codechen.ai.chatclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author：Java陈序员
 * @date：2025-7-16 10:58
 * @description：
 */
@RestController
public class ChatController {

//    private final ChatClient chatClient;
//
//    public ChatController(ChatClient.Builder chatClientBuilder) {
//        this.chatClient = chatClientBuilder.build();
//    }

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private OpenAiChatModel openAiChatModel;

//    @Autowired
//    private OpenAiApi baseOpenAiApi;

    @GetMapping("chat1")
    public String chat1(@RequestParam String message) {
        return chatClient
                // 提示词
                .prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping(value = "chat2", produces = "text/html;charset=utf-8")
    public Flux<String> chat2(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }

    @GetMapping("/chat3")
    public String chat3(@RequestParam String message) {
        OpenAiApi deepSeekApi = OpenAiApi.builder()
                .baseUrl("https://api.deepseek.com")
                .apiKey("sk-8e246d7869ad41fab44f5c553bd1edfb")
                .build();

        OpenAiChatModel deepSeekChatModel = openAiChatModel.mutate()
                .openAiApi(deepSeekApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("deepseek-chat")
                        .temperature(0.7)
                        .build())
                .build();

        return ChatClient.builder(deepSeekChatModel)
                .build()
                .prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping("chat4")
    public String chat4(@RequestParam String message) {
        return chatClient.prompt()
                .system("你是Java陈序员，一个从事Java后端开发的编程工程师")
                .user(message)
                .call()
                .content();
    }
}
