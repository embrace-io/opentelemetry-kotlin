# OpenTelemetry Kotlin

An implementation of the [OpenTelemetry specification](https://opentelemetry.io/docs/specs/otel/) as a Kotlin
Multiplatform Library, developed by [embrace.io](https://embrace.io/).

This API operates in 2 modes:
1. Compatibility mode, where it acts as a façade for the [OpenTelemetry Java SDK](https://github.com/open-telemetry/opentelemetry-java)
2. Regular mode, where it captures telemetry via a Kotlin Multiplatform (KMP) implementation

[![codecov](https://codecov.io/github/embrace-io/opentelemetry-kotlin/branch/main/graph/badge.svg?token=GQJYEOUSAU)](https://codecov.io/github/embrace-io/opentelemetry-kotlin)

## Supported targets

The following targets are supported:

- Android (API >=21)
- JVM (JDK >= 8)

Other targets compile but are not considered sufficiently tested to count as 'supported' at this current time.

## Supported OTel APIs

- Tracing
- Logging

## Getting Started

### Regular mode

1. Add the following dependencies to your Android/Java project:

```
dependencies {
    implementation("io.embrace.opentelemetry.kotlin:opentelemetry-kotlin:<latest-version>")
    implementation("io.embrace.opentelemetry.kotlin:opentelemetry-kotlin-implementation:<latest-version>")
}
```

2. Initialize the SDK:

```
val otelKotlin = createOpenTelemetryKotlin()
```

3. Use the Kotlin API in your app

### Compatibility mode

Compatibility mode allows you to use a Kotlin API that under the hood uses the OpenTelemetry Java SDK under the hood to export telemetry.
This can be helpful if you already use the Java implementation, or don't want to use the Kotlin implementation for whatever reason.

1. Add the following dependencies to your Android/Java project:

```
dependencies {
    implementation("io.embrace.opentelemetry.kotlin:opentelemetry-kotlin:<latest-version>")
    implementation("io.embrace.opentelemetry.kotlin:opentelemetry-kotlin-compat:<latest-version>")
}
```

2. Wrap your existing [OTel Java](https://github.com/open-telemetry/opentelemetry-java) instance:

```
val otelJava = io.opentelemetry.sdk.OpenTelemetrySdk.builder().build()
val otelKotlin = otelJava.toOtelKotlinApi()
```

3. Use the Kotlin API instead of the Java API in your app

## Example usage

### Tracing API

```
val tracer = otelKotlin.tracerProvider.getTracer(
    name = "kotlin-example-app",
    version = "0.1.0"
)
tracer.createSpan("my_span")
```

### Logging API

```
val logger = otelKotlin.loggerProvider.getLogger("my_logger")
logger.log("Hello, World!")
```

### Example Apps

Example usage of the library can be found [here](examples).

## Feedback/bugs

Got feedback or found a bug? Please open a GitHub issue or contact support@embrace.io and we'll get back to you.

## Snapshot versions

Every day, a snapshot version is published to the maven snapshots repository. You can check the current snapshot version in [gradle.properties](gradle.properties).
To use a snapshot version in your app, you need to add the Central Portal snapshot repository, like this:

```
repositories {
    ...
    maven {
        name = "Central Portal Snapshots"
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        content {
            includeGroup("io.embrace")
        }
    }
    ...
}
```
