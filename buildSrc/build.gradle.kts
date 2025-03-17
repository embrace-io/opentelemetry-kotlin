import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(libs.kotlinMultiplatform)
    implementation(libs.agp)
}

gradlePlugin {
    plugins {
        create("otelBuildPlugin") {
            id = "io.embrace.otel.build-logic"
            implementationClass = "io.embrace.otel.BuildPlugin"
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
    }
}
