# OpenTelemetry Kotlin

An implementation of the [OpenTelemetry specification](https://opentelemetry.io/docs/specs/otel/) as a Kotlin
Multiplatform Library, developed by [embrace.io](https://embrace.io/).

Currently this API is a facade for the [OpenTelemetry Java SDK](https://github.com/open-telemetry/opentelemetry-java). In the near future this library will provide its own KMP implementation of the OpenTelemetry specification.

## Supported targets

The following targets are supported:

- Android (API >=21)
- JVM (JDK >= 8)

Support for iOS and other platforms is planned for the future.

## Supported OTel APIs

- Tracing
- Logging

## Getting Started

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
val otelKotlin = OpenTelemetryInstance.decorateJavaApi(otelJava)
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
