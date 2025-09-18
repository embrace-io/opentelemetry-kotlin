# opentelemetry-kotlin-benchmark-jvm

This module provides benchmarks of `opentelemetry-kotlin` using `kotlinx-benchmark`.
Common operations using the tracing/logging APIs are benchmarked on the JVM for:

1. `opentelemetry-kotlin` API, `opentelemetry-kotlin` implementation (under the `kotlin` package)
2. `opentelemetry-kotlin` API, `opentelemetry-java` implementation (under the `compat` package)
3. `opentelemetry-java` API, `opentelemetry-java` implementation (under the `java` package)

Wherever possible the test case will attempt to invoke the same behavior across all implementations.
E.g. when creating a span the same parameters should be passed in. This will make result
comparison more meaningful.

Benchmarks can be run via `./gradlew jvmPerfBenchmark`
