
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
}

group = "com.camilo"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.cio.EngineMain"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(libs.khealth)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.openapi)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.jackson)
    implementation(libs.ktor.server.cio)
    implementation(libs.logback.classic)



    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    implementation(libs.pdf.box)

    implementation(libs.poi)
    implementation(libs.opencsv)
    implementation(libs.langchain4j)
    implementation(libs.langchain4j.openai)
    implementation(libs.langchain4j.neo4j)
    implementation(libs.neo4j.java.driver)
    implementation(libs.embeddings.all.minilm)



    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

}
