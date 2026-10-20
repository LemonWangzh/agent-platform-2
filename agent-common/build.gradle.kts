// ============================================================
// agent-common：公共模块（工具类、通用 DTO、公共配置）
// 这是一个"纯库"模块：给别的模块引用，自己不独立运行、不打可执行包
// ============================================================
plugins {
    // java-library：标准 Java 库插件，比 java 多支持 api 依赖
    // （api = 依赖会传递给引用方；implementation = 不传递）
    `java-library`
    // 应用公共约定（Java 17、BOM 版本、测试依赖，见 buildSrc）
    id("agent.java-conventions")
}
