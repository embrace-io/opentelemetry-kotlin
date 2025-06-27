plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("io.embrace.otel.build-logic")
    id("signing")
    id("com.vanniktech.maven.publish")
}

kotlin {
    sourceSets {
        applyDefaultHierarchyTemplate()

        val jvmAndAndroidMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                api(project(":opentelemetry-kotlin"))
                implementation(project(":opentelemetry-java-typealiases"))
                implementation(project.dependencies.platform(libs.opentelemetry.bom))
                implementation(libs.opentelemetry.api)
            }
        }
        val androidMain by getting {
            dependsOn(jvmAndAndroidMain)
        }
        val jvmMain by getting {
            dependsOn(jvmAndAndroidMain)
        }
        val jvmTest by getting {
            dependencies {
                implementation(project(":compat-shared"))
            }
        }
    }
}
