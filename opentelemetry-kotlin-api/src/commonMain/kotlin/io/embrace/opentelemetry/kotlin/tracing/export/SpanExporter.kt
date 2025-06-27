package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ExportResult

/**
 * An interface for exporting spans to an arbitrary destination.
 */
@ExperimentalApi
public interface SpanExporter {

    /**
     * Exports a batch of spans. This operation is considered successful if the implementation
     * returns [ExportResult.SUCCESS]. If the export operation fails the batch must be dropped.
     */
    public fun export(telemetry: List<SpanData>): ExportResult

    /**
     * Requests the exporter to flush any buffered telemetry since the last call to [export].
     */
    public fun forceFlush(): ExportResult

    /**
     * Shuts down the exporter and completes cleanup tasks necessary.
     */
    public fun shutdown(): ExportResult
}
