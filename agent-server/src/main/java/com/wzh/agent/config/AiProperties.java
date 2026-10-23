package com.wzh.agent.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 多供应商大模型配置，对应 application.yml 里的 app.ai.providers 小节。
 *
 * yml 长这样（providers 里每个 key 对应一个 Provider 对象）：
 *   app:
 *     ai:
 *       providers:
 *         glm:
 *           base-url: https://open.bigmodel.cn/api/paas/v4
 *           api-key: xxx
 *           model: GLM-4.7
 *           temperature: 0.5
 */
@Data
@ConfigurationProperties(prefix = "app.ai")
public class AiProperties {

    /** key = 供应商名（如 glm、deepseek），与 ChatConfig 里的 Bean 名对应 */
    private Map<String, Provider> providers = new HashMap<>();

    /** 单个供应商的连接信息 */
    @Data
    public static class Provider {

        /** 接口地址（OpenAI 兼容端点） */
        private String baseUrl;

        /** API 密钥，建议环境变量注入 */
        private String apiKey;

        /** 默认模型名 */
        private String model;

        /** 温度：越低越严谨，越高越发散 */
        private Double temperature = 0.5;

    }
}
