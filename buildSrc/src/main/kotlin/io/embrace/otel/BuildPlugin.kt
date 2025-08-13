package io.embrace.otel

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class BuildPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
            val kotlin = project.extensions.getByType(KotlinMultiplatformExtension::class.java)
            project.configureKotlin(kotlin)
            project.configureDetekt()
            project.configureBinaryCompatValidation()
            project.configureExplicitApiMode(kotlin)
        }
        project.configurePublishing()
    }
}
