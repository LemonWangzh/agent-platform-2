package com.wzh.agent.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 多供应商 ChatClient 配置。
 *
 * 约定：Bean 方法名 = yml 里 app.ai.providers 下的供应商名。
 * 这样注入方可以直接用 Map<String, ChatClient> 收集全部供应商
 * （Spring 会自动按 Bean 名装进 Map 的 key）。
 *
 * 新增供应商两步：
 *   1. yml 的 app.ai.providers 下加一个小节
 *   2. 这里照抄一个 @Bean 方法（改名字和取的 key）
 */
@Configuration
@EnableConfigurationProperties(AiProperties.class)
public class ChatConfig {

    /** 智谱 GLM */
    @Bean
    ChatClient glm(AiProperties props) {
        return buildClient(props.getProviders().get("glm"));
    }

    /** DeepSeek */
    @Bean
    ChatClient deepseek(AiProperties props) {
        return buildClient(props.getProviders().get("deepseek"));
    }

    /** 按配置构建一个 OpenAI 兼容协议的 ChatClient */
    private ChatClient buildClient(AiProperties.Provider provider) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .options(OpenAiChatOptions.builder()
                        .baseUrl(provider.getBaseUrl())
                        .apiKey(provider.getApiKey())
                        .model(provider.getModel())
                        .temperature(provider.getTemperature())
                        .build())
                .build();
        return ChatClient.create(model);
    }
}
