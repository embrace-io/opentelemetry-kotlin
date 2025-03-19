plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.application")
}

dependencies {
    implementation(project(":examples:app-shared"))
}

android {
    namespace = "io.embrace.opentelemetry.example"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.embrace.opentelemetry.example"
        minSdk = 21
        targetSdk = 35
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
