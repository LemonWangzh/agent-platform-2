package com.wzh.agent;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 上下文启动测试：验证 Spring 容器能正常装配。
 * 给一个假 api-key，让测试不依赖真实的大模型密钥。
 */
@SpringBootTest(properties = "spring.ai.openai.api-key=test-key")
class AgentApplicationTest {

    @Test
    void contextLoads() {
    }
}
