plugins {
    id("java-platform")
    id("signing")
    id("com.vanniktech.maven.publish")
    id("io.embrace.otel.build-logic")
}

description = "OpenTelemetry Kotlin Bill of Materials"

dependencies {
    constraints {
        api(project(":opentelemetry-kotlin"))
        api(project(":opentelemetry-kotlin-api"))
        api(project(":opentelemetry-kotlin-api-ext"))
        api(project(":opentelemetry-kotlin-noop"))
        api(project(":opentelemetry-kotlin-implementation"))
        api(project(":opentelemetry-kotlin-model"))
        api(project(":opentelemetry-kotlin-compat"))
        api(project(":opentelemetry-kotlin-primitives"))
        api(project(":opentelemetry-kotlin-testing"))
        api(project(":opentelemetry-java-typealiases"))
    }
}