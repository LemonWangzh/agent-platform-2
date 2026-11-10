package com.wzh.agent.controller;

import com.wzh.agent.films.ActorFilms;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

/**
 * 大模型对话接口：按供应商名路由到对应的 ChatClient。
 *
 * Spring 的技巧：注入 Map<String, ChatClient> 时，
 * key 自动 = Bean 名（glm / deepseek），value = 对应的 Bean，
 * 以后加新供应商这个类完全不用改。
 */
@RestController
@RequestMapping("/chat")
class AiChatController {


    @Resource
    private ChatClient chatClient;

    private BeanOutputConverter<List<ActorFilms>> converter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<ActorFilms>>() {
    });

    /**
     * 示例：
     *   GET /ai/glm?userInput=你好
     *   GET /ai/deepseek?userInput=你好
     */
    @GetMapping("/simpleInput")
    public String generation(@RequestParam String userInput) {
        return chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }


    @GetMapping("/outMoviesFilms")
    public List<ActorFilms> actorFilmsList(@RequestParam String input){
        return chatClient.prompt().user(input).call().entity(new ParameterizedTypeReference<>() {
        });
    }

    /**
     * 结构化输出：转成 Java 对象返回。
     * 注意：不要用 useProviderStructuredOutput()——
     * 它会走供应商原生 json_schema 协议，DeepSeek/GLM 都不支持，会报 400。
     * 默认的 prompt 方式（格式要求写进提示词 + 本地解析）对所有供应商通用。
     */
    @GetMapping("/specChat")
    public ActorFilms specChat() {
        return chatClient.prompt()
                .user(u -> u.text("""
                        Generate the filmography for a random actor.

                        要求：
                        - 只返回一个 JSON 对象，包含 actor（演员名）和 movies（电影名列表）两个字段
                        - JSON 之外不要有任何解释或多余内容
                        """))
                .call()
                .entity(ActorFilms.class, ChatClient.EntityParamSpec::validateSchema);
    }


    @GetMapping("/fluxChat")
    public Flux<String> fluxChat(@RequestParam String input){
        return this.chatClient.prompt().user(u -> u.text("""
                Generate the filmography for a random actor.{format}
                """).param("format", this.converter.getFormat())).stream().content();
    }

    @GetMapping("findBooksByCountry")
    public String findBooksByCountry(@RequestParam String country){
        return this.chatClient.prompt().user(u -> u.text("Tell me five famous books from the {country}.")
                .param("country", country)).call().content();
    }


    @GetMapping("/userPrompt")
    public String userPrompt(@RequestParam String input){
        Map<String, Object> userPromptExt = Map.of("messageId", "msg-123",
                "userId", "user-001",
                "timestamp", System.currentTimeMillis());
        return this.chatClient.prompt().user(u ->u.text(input).metadata(userPromptExt))
                .call().content();
    }

    @GetMapping("/systemMessage")
    public String systemMessageTest(@RequestParam String input){
        return chatClient.prompt().user(input).call().content();
    }


    @GetMapping("/systemMessage2")
    public String systemMessageTest2(@RequestParam("message") String input, String voice){
        return chatClient.prompt()
                .user(input).system(sp -> sp.param("voice", voice)).call().content();
    }




}
