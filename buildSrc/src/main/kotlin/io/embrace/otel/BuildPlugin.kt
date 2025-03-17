package io.embrace.otel

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class BuildPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val buildLogic = project.extensions.create("buildLogic", BuildLogicExtension::class.java)
        val kotlin = project.project.extensions.getByType(KotlinMultiplatformExtension::class.java)
        configureKotlin(kotlin)
        project.configureAndroid()
        project.configureDetekt()
        project.configureBinaryCompatValidation(buildLogic)
        project.configureExplicitApiMode(buildLogic, kotlin)
    }
}
