@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.k2j.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.j2k.bridge.context.toOtelKotlin

/**
 * Returns the root Context.
 */
public fun Context.Companion.root(): Context = OtelJavaContext.root().toOtelKotlin()

/**
 * Returns the current context.
 */
public fun Context.Companion.current(): Context = OtelJavaContext.current().toOtelKotlin()
