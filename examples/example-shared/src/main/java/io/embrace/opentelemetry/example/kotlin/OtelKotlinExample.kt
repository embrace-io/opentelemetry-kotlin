package io.embrace.opentelemetry.example.kotlin

fun createInstrumentationWithOtelKotlin() {
    val api = OtelKotlinApi()
    val tracer = api.tracer.getTracer(
        name = "kotlin-example-app",
        version = "0.1.0"
    )
    tracer.createSpan("my_span").end()
    api.logger.logRecordBuilder().setBody("Hello, World!").emit()
}

