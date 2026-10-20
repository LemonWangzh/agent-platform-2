// ============================================================
// buildSrc 是 Gradle 的一个特殊目录（名字固定不能改）
// 作用：这里写的构建脚本会被 Gradle 自动编译成"自定义插件"，
//       供各个子模块引用，实现"公共构建配置只写一份"
//
// 本文件：buildSrc 自身的构建配置，一般不用动
// ============================================================
plugins {
    // kotlin-dsl：让我们能用 Kotlin 写自定义插件（Gradle 自动提供，不用装）
    `kotlin-dsl`
}

repositories {
    maven("https://maven.aliyun.com/repository/gradle-plugin")
    gradlePluginPortal()
}

dependencies {
    // 把 Spring 的依赖管理插件加到 buildSrc 的类路径上，
    // 这样下面约定插件脚本里才能使用 id("io.spring.dependency-management")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.7")
}
