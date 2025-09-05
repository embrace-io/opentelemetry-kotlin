# opentelemetry-kotlin-api

This module contains the main public API of the SDK as prescribed by the OTel specification.
It is intended that library consumers will spend the vast majority of their time using symbols from
this module, with the exception of the entrypoints supplied in `opentelemetry-kotlin-compat`,
`opentelemetry-kotlin-implementation`, or  `opentelemetry-kotlin-noop`.

The intention of this module is to follow the OTel specification as closely as possible. Any
syntactic sugar that diverges from the spec should live in `opentelemetry-kotlin-api-ext` instead,
so that end-users can choose whether to use extensions to the base behavior.

## Guidelines for APIs

Please see the [contributing doc](../CONTRIBUTING.md) for design guidance on APIs. In short,
use interfaces to obscure concrete types & avoid default implementations as these tend to blend
separate concerns.
