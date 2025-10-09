import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
}

version = project.properties["version"] as String

dependencies {
    implementation(gradleApi())
    implementation(libs.kotlinMultiplatform)
    implementation(libs.agp)
    implementation(libs.detekt.gradle.plugin)
    implementation(libs.mavenPublish)
    implementation(libs.binary.compatibility.validator)
}

gradlePlugin {
    plugins {
        create("otelBuildPlugin") {
            id = "io.embrace.opentelemetry.kotlin.build-logic"
            implementationClass = "io.embrace.opentelemetry.kotlin.BuildPlugin"
        }
    }
}

// ensure the Kotlin + Java compilers both use the same language level.
project.tasks.withType(JavaCompile::class.java).configureEach {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}

// ensure the Kotlin + Java compilers both use the same language level.
kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
    }
}
