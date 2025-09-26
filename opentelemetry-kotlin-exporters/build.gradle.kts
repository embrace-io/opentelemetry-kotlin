plugins {
    kotlin("jvm")
    id("io.embrace.opentelemetry.kotlin.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
}

dependencies {
    implementation(project(":opentelemetry-kotlin-api"))
    implementation(project(":opentelemetry-kotlin-exporters-jvm"))
    implementation(libs.protobuf.kotlin)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.encoding)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.kotlin.test)
    testImplementation(project(":opentelemetry-kotlin-test-fakes"))
}
