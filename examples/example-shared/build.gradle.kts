plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java")
}

dependencies {
    api(project(":opentelemetry-kotlin-api"))
    implementation(project(":compat-kotlin-to-official"))
    implementation(project(":compat-official-to-kotlin"))
    api(platform(libs.opentelemetry.bom))
    api(libs.opentelemetry.api)
    implementation(libs.opentelemetry.sdk)
}
