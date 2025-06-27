plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("io.embrace.otel.build-logic")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        applyDefaultHierarchyTemplate()

        val jvmAndAndroidMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                api(project(":opentelemetry-kotlin-api"))
                implementation(libs.kotlin.serialization)
                implementation("org.jetbrains.kotlin:kotlin-test") {
                    exclude(group = "junit")
                    exclude(group = "org.junit")
                }
            }
        }
        val androidMain by getting {
            dependsOn(jvmAndAndroidMain)
        }
        val jvmMain by getting {
            dependsOn(jvmAndAndroidMain)
        }
    }
}
