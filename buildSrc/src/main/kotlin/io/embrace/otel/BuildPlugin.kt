package io.embrace.otel

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class BuildPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val kotlin = project.project.extensions.getByType(KotlinMultiplatformExtension::class.java)
        project.configureKotlin(kotlin)
        project.configureAndroid(kotlin)
        project.configureDetekt()
        project.configureBinaryCompatValidation()
        project.configureExplicitApiMode(kotlin)
        project.configurePublishing()
    }
}
