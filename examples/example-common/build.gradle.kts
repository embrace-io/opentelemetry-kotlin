plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    jvm {}
    js(IR) {
        nodejs()
        browser()
        binaries.library()
    }
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":opentelemetry-kotlin"))
                implementation(project(":opentelemetry-kotlin-implementation"))
            }
        }
    }
}
