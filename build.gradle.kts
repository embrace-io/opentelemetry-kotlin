plugins {
    id("com.android.kotlin.multiplatform.library") apply false
    id("org.jetbrains.kotlin.multiplatform") apply false
    id("com.vanniktech.maven.publish") apply false
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
}

group = "io.embrace"
version = project.version

kover {
    merge {
        subprojects { project ->
            val testCoverageProjects = listOf(
                "opentelemetry-kotlin-api",
                "opentelemetry-kotlin-api-ext",
                "opentelemetry-kotlin-compat",
                "opentelemetry-kotlin-implementation",
                "opentelemetry-kotlin-noop",
            )
            return@subprojects testCoverageProjects.contains(project.name)
        }
    }
    reports {
        filters {
            excludes {
                androidGeneratedClasses()
                classes("*.BuildConfig")
            }
        }
    }
}
