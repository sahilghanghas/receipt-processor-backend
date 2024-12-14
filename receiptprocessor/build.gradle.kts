plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"
    id("com.google.devtools.ksp") version "1.9.25-1.0.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.4"
    id("io.micronaut.aot") version "4.4.4"
}

version = "0.1"
group = "com.fetch"

val kotlinVersion= project.properties["kotlinVersion"]
repositories {
    gradlePluginPortal()
    mavenCentral()
    maven { url = uri("https://repo.micronaut.io") }
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut:micronaut-runtime")
    //implementation("io.micronaut.http:micronaut-http-server-netty")
    //implementation("io.micronaut.serialization:micronaut-serialization")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("io.micronaut:micronaut-core:4.7.1")
    implementation("io.micronaut:micronaut-runtime:4.7.1")
    implementation("io.micronaut.openapi:micronaut-openapi:4.10.0")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.0")
    //implementation("io.micronaut.openapi:micronaut-swagger-ui:4.10.0")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    // https://mvnrepository.com/artifact/io.micronaut/micronaut-http-server-netty
    runtimeOnly("io.micronaut:micronaut-http-server-netty:4.7.1")
    runtimeOnly("io.micronaut.serde:micronaut-serde-jackson:2.12.0")
    //runtimeOnly("io.micronaut.openapi:micronaut-swagger-ui:4.10.0")
    testImplementation("io.micronaut:micronaut-http-client")
}


application {
    mainClass = "com.fetch.Application"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
}


graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.fetch.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}


