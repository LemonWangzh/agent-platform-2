package com.wzh.agent.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 大模型对话接口：按供应商名路由到对应的 ChatClient。
 *
 * Spring 的技巧：注入 Map<String, ChatClient> 时，
 * key 自动 = Bean 名（glm / deepseek），value = 对应的 Bean，
 * 以后加新供应商这个类完全不用改。
 */
@RestController
class MyController {


    @Resource
    private ChatClient chatClient;

    /**
     * 示例：
     *   GET /ai/glm?userInput=你好
     *   GET /ai/deepseek?userInput=你好
     */
    @GetMapping("/ai")
    String generation(@RequestParam String userInput) {
        return chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }
}
