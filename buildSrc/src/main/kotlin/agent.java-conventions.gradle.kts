// ============================================================
// 约定插件（Convention Plugin）：所有子模块的"公共构建规则"
// 放在 buildSrc/src/main/kotlin/ 下的 .gradle.kts 文件会自动变成插件，
// 插件名 = 文件名，所以子模块里用 id("agent.java-conventions") 引用它
//
// 子模块一旦应用了这个插件，就自动获得：
//   ✅ Java 17 编译环境
//   ✅ Spring Boot / Spring Cloud 的统一版本管理（BOM）
//   ✅ 公共测试依赖（JUnit + Spring Test）
// ============================================================
plugins {
    // 启用 Java 构建（编译、打包、测试任务都来自它）
    java
    // Spring 的依赖管理插件：配合下面的 BOM 实现"依赖不写版本号，统一由 BOM 决定"
    id("io.spring.dependency-management")
}

// Java 编译环境配置
java {
    toolchain {
        // 统一用 Java 17 编译，即使机器上装了多个 JDK，
        // Gradle 也会自动找到（没有会自动下载）对应版本
        languageVersion = JavaLanguageVersion.of(17)
    }
}

// ============================================================
// 版本统一管理（BOM = Bill of Materials，版本清单）
// 导入后，所有 Spring 相关依赖都不用写版本号，
// 版本升级只改这里，全部子模块一起生效
// ============================================================
dependencyManagement {
    imports {
        // Spring Boot 全家桶的版本清单
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.1.0")
        // Spring Cloud 的版本清单（注意：要和 Boot 版本配套，查官方兼容表）
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2025.1.2")
        // 以后接 Spring AI / Spring Cloud Alibaba，在这里加对应的 mavenBom(...)
    }
}

// 公共依赖：每个子模块都会自动带上
dependencies {
    // Spring Boot 官方测试套件（JUnit 5 + Spring Test + AssertJ 等）
    "testImplementation"("org.springframework.boot:spring-boot-starter-test")
    // 让测试报告能正确识别 JUnit 平台
    "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
}

// 测试统一走 JUnit 5 平台
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
