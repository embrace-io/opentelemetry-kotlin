# Built-in Log Record Processors

This package contains built-in implementations of `LogRecordProcessor` as specified in the OpenTelemetry specification.

## Processors

### NoopLogRecordProcessor

A no-operation processor that does nothing with log records. Useful for:
- Testing scenarios where you want to disable log processing
- Default configuration where no processing is needed
- Placeholder in configurations

```kotlin
val processor = NoopLogRecordProcessor.INSTANCE
```

### SimpleLogRecordProcessor  

A processor that immediately exports each log record as it is emitted. Useful for:
- Debugging and development
- Low-throughput scenarios
- Situations where immediate export is required

```kotlin
val exporter = MyLogRecordExporter()
val processor = SimpleLogRecordProcessor(exporter)
```

### BatchLogRecordProcessor

A processor that batches log records and exports them in groups. Useful for:
- Production environments with high throughput
- Reducing export overhead by batching
- Configurable buffer sizes

```kotlin
val exporter = MyLogRecordExporter()
val processor = BatchLogRecordProcessor(
    exporter = exporter,
    maxBatchSize = 512,    // Export when batch reaches this size
    maxQueueSize = 2048    // Drop records if queue exceeds this size
)
```

## Using with CompositeLogRecordProcessor

All processors can be used together:

```kotlin
val noopProcessor = NoopLogRecordProcessor.INSTANCE
val simpleProcessor = SimpleLogRecordProcessor(immediateExporter)
val batchProcessor = BatchLogRecordProcessor(batchExporter)

val composite = CompositeLogRecordProcessor(
    listOf(noopProcessor, simpleProcessor, batchProcessor),
    errorHandler
)
```

## Thread Safety

- `NoopLogRecordProcessor`: Thread-safe (no shared state)
- `SimpleLogRecordProcessor`: Thread-safe (delegates to exporter)
- `BatchLogRecordProcessor`: Thread-safe (uses ReentrantReadWriteLock and thread-safe collections)

## Error Handling

All processors follow the OpenTelemetry specification for error handling:
- Errors in export operations do not affect subsequent processing
- Lifecycle operations (`forceFlush`, `shutdown`) return appropriate result codes
- Processors handle concurrent access safely during shutdown