// ============================================================
// 根工程的 build.gradle.kts
// 多模块项目中，根工程自己不写代码，只干两件事：
//   1. 声明"全家共用的插件版本"（apply false = 这里只定版本，不真启用）
//   2. 声明所有模块统一的坐标（group/version）
//
// 各子模块的公共构建逻辑在：
//   buildSrc/src/main/kotlin/agent.java-conventions.gradle.kts（约定插件）
// ============================================================
plugins {
    // 声明 Spring Boot 插件的版本号，供子模块（agent-server）使用
    // 版本号只在这一个地方写，所有子模块共用，避免版本不一致
    id("org.springframework.boot") version "4.1.0" apply false
}

// 所有模块（含子模块）统一的 Maven 坐标和依赖下载仓库
// group + 模块名 = 打出来的 jar 包在全世界的唯一标识
allprojects {
    group = "com.wzh.agent"
    version = "0.0.1-SNAPSHOT"   // SNAPSHOT = 开发中的版本，随时可变

    // 依赖（jar 包）从哪里下载：所有子模块统一用这两个仓库
    repositories {
        // 阿里云 Maven 镜像（国内访问快）
        maven("https://maven.aliyun.com/repository/public")
        // Maven 中央仓库（官方源，兜底）
        mavenCentral()
    }
}
