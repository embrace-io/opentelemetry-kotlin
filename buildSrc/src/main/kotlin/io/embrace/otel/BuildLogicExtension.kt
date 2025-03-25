package io.embrace.otel

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

/**
 * Controls build logic for a given module.
 */
abstract class BuildLogicExtension(objectFactory: ObjectFactory) {

    /**
     * Whether this module forms part of the SDK's public API or not. This enables binary compatibility checks, Kotlin's
     * explicit API mode and Dokka generation.
     */
    val containsPublicApi: Property<Boolean> =
        objectFactory.property(Boolean::class.java).convention(false)

    /**
     * The list of target platforms to build for this module.
     */
    val targetPlatforms: ListProperty<TargetPlatform> =
        objectFactory.listProperty(TargetPlatform::class.java).convention(TargetPlatform.values().toList())
}
