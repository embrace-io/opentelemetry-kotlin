plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java")
    application
}

application {
    mainClass.set("io.embrace.opentelemetry.example.kotlin.JvmAppKt")
}

dependencies {
    implementation(project(":examples:example-common"))
}
