package io.embrace.opentelemetry.kotlin.integration.test

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalApi::class)
internal class TracerExportTest {

    private lateinit var harness: IntegrationTestHarness

    @BeforeTest
    fun setUp() {
        harness = IntegrationTestHarness()
    }

    @Test
    fun `test span exported by tracer`() {
        val span = harness.tracer.createSpan("test") {
            setStringAttribute("foo", "bar")
        }
        span.end()
        harness.assertSpans(1, "span_minimal.json")
    }
}
