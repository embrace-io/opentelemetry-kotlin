plugins {
    kotlin("jvm")
    `java-library`
}

repositories {
    mavenCentral()
}

java {
    withSourcesJar()
}

dependencies {
    implementation(libs.detekt.api)
    testImplementation(libs.detekt.test)
    testImplementation(kotlin("test"))
}
