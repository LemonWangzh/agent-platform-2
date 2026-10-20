// ============================================================
// agent-server：可运行的主服务模块
// Spring Boot 启动类（AgentApplication）在这个模块里
// 打包结果是可直接 java -jar 运行的可执行 jar
// ============================================================
plugins {
    // 应用公共约定（Java 17、BOM 版本、测试依赖，见 buildSrc）
    id("agent.java-conventions")
    // Spring Boot 插件：提供 bootRun（开发运行）、bootJar（打可执行包）等
    // 版本号不用写，统一由根工程 build.gradle.kts 决定
    id("org.springframework.boot")
}

// 本模块自己的依赖
dependencies {
    // 引用公共模块（模块间依赖用 project(":模块名")，不写版本）
    implementation(project(":agent-common"))

    // Spring MVC + 内嵌 Tomcat，提供 REST 接口能力
    // 注意：没有版本号，版本由约定插件里的 BOM 统一决定
    implementation("org.springframework.boot:spring-boot-starter-web")
}
