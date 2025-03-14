package io.embrace.otel

import kotlinx.validation.ApiValidationExtension
import org.gradle.api.Project

fun Project.configureBinaryCompatValidation(buildLogic: BuildLogicExtension) {
    project.pluginManager.apply("binary-compatibility-validator")
    project.afterEvaluate {
        val apiValidation = project.extensions.getByType(ApiValidationExtension::class.java)
        apiValidation.validationDisabled = !buildLogic.containsPublicApi.get()
    }
}
