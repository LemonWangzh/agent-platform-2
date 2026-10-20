// ============================================================
// settings.gradle.kts —— Gradle 的"项目总设置"
// 作用：告诉 Gradle 这个工程叫什么名字、有哪些子模块、去哪里下载东西
// 注意：本文件在打开项目时最先被读取，所有模块共享这里的仓库配置
// ============================================================

// pluginManagement：配置"构建插件"从哪里下载
// （插件 = 扩展 Gradle 能力的工具，比如 Spring Boot 插件负责打可执行包）
pluginManagement {
    repositories {
        // 阿里云镜像（国内访问快），优先从这里找
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        // 找不到再走 Gradle 官方插件仓库
        gradlePluginPortal()
    }
}

// 注意：项目依赖（jar 包）的下载仓库不在这里配，
// 统一配在根工程 build.gradle.kts 的 allprojects 里（API 更稳定）

// 工程名（打包时的 artifactId 就基于它）
rootProject.name = "agent-platform-2"

// ============================================================
// 子模块列表：新增一个子模块需要两步
//   1. 建一个同名目录，里面放 build.gradle.kts
//   2. 在下面 include 里加上目录名
// ============================================================
include(
    "agent-common",   // 公共模块：工具类、通用 DTO、公共配置
    "agent-server",   // 主服务模块：Spring Boot 启动类所在的地方
)
