package io.embrace.opentelemetry.example.java

fun createInstrumentationWithOtelJava() {
    val api = OtelJavaApi()
    val span = api.tracer.spanBuilder("my_span").startSpan()
    span.end()
    api.logger.logRecordBuilder().setBody("Hello, World!").emit()
}

