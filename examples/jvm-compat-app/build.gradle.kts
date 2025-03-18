plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java")
}

dependencies {
    implementation(project(":opentelemetry-kotlin"))
    implementation(project(":examples:compat-app-shared"))
}
