package com.codechen.ai.chatclient.controller;

import com.codechen.ai.chatclient.entity.ActorFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

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
                .system(sp -> sp.param("language", "Vue"))
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

    @GetMapping("chat5")
    public ActorFilms chat5(@RequestParam String message) {

        return chatClient.prompt()
                .user(message)
                .call()
                .entity(ActorFilms.class);
    }

    @GetMapping("chat6")
    public List<ActorFilms> chat6(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<ActorFilms>>() {
                });
    }

    @GetMapping("chat7")
    public ActorFilms chat7(@RequestParam String message) {
        return chatClient.prompt()
                .user(u ->
                        u.text("""
                                随机推荐演员 {actor} 主演的几部电影
                                """).param("actor", message))
                .call()
                .entity(ActorFilms.class);
    }

    @GetMapping("chat8")
    public List<ActorFilms> chat8(@RequestParam String message) {
        var converter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<ActorFilms>>() {
        });

        Flux<String> flux = chatClient.prompt()
                .user(u -> u.text("""
                                随机生成演员 {actor} 主演的电影 {format}
                                """)
                        .param("actor", message)
                        .param("format", converter.getFormat()))
                .stream()
                .content();

        String content = flux.collectList().block().stream().collect(Collectors.joining());

        return converter.convert(content);
    }

    @GetMapping("chat9")
    public String chat9(@RequestParam String message) {
        return chatClient.prompt()
                .user(u ->
                        u.text("""
                                随机推荐演员 <actor> 主演的几部电影
                                """).param("actor", message))
                .templateRenderer(StTemplateRenderer.builder()
                        .startDelimiterToken('<')
                        .endDelimiterToken('>')
                        .build())
                .call()
                .content();
    }

    @GetMapping("chat10")
    public String chat10(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .system("你是隔壁老王，专门修破鞋")
                .call()
                .content();
    }

    @GetMapping("chat11")
    public String chat11(@RequestParam String message) {
        SimpleLoggerAdvisor customLogger = new SimpleLoggerAdvisor(
                request -> "用户输入：" + request.prompt().getUserMessage().toString(),
                response -> "AI 响应：" + response.getResult().toString(),
                2
                );

        return chatClient.prompt()
                .user(message)
                .advisors(new SimpleLoggerAdvisor())
                .advisors(customLogger)
                .call()
                .content();
    }
}
