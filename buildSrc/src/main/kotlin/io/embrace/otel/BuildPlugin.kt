package io.embrace.otel

import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val buildLogic = project.extensions.create("buildLogic", BuildLogicExtension::class.java)
        project.configureKotlin()
        project.configureAndroid()
        project.configureDetekt()
        project.configureBinaryCompatValidation(buildLogic)
    }

}
