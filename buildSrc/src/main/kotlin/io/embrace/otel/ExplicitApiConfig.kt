package io.embrace.otel

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.configureExplicitApiMode(module: BuildLogicExtension, kotlin: KotlinMultiplatformExtension) {
    project.afterEvaluate {
        if (module.containsPublicApi.get()) {
            kotlin.compilerOptions {
                freeCompilerArgs.set(freeCompilerArgs.get().plus("-Xexplicit-api=strict"))
            }
        }
    }
}
