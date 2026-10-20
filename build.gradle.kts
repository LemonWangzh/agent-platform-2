plugins {
    java
}

group = "com.wzh.agent"
version = "0.0.1-SNAPSHOT"

repositories {
    // 国内镜像，加速依赖下载；不需要可删除
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
