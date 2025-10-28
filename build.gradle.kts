plugins {
    id("com.android.kotlin.multiplatform.library") apply false
    id("org.jetbrains.kotlin.multiplatform") apply false
    id("com.vanniktech.maven.publish") apply false
    id("org.jetbrains.kotlinx.kover") version "0.9.3"
}

group = "io.embrace"
version = project.version

kover {
    merge {
        subprojects { project ->
            project.findProperty("io.embrace.opentelemetry.kotlin.enableCodeCoverage")?.toString()?.toBoolean() ?: true
        }
    }
    reports {
        filters {
            excludes {
                androidGeneratedClasses()
                classes("*.BuildConfig", "io.opentelemetry.proto.*")
            }
        }
    }
}
