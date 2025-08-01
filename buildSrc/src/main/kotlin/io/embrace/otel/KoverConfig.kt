package io.embrace.otel

import org.gradle.api.Project

fun Project.configureKover() {
    project.pluginManager.apply("org.jetbrains.kotlinx.kover")
}
