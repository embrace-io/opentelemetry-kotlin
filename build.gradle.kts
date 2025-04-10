import java.time.Duration

plugins {
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.multiplatform") apply false
    id("com.vanniktech.maven.publish") apply false
    alias(libs.plugins.nexus.publish)
}

group = "io.embrace"
version = project.version

nexusPublishing {
    repositories {
        sonatype {
            username = System.getenv("SONATYPE_USERNAME")
            password = System.getenv("SONATYPE_PASSWORD")
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
        }
    }
    transitionCheckOptions {
        maxRetries.set(60)
        delayBetween.set(Duration.ofSeconds(20))
    }
    connectTimeout.set(Duration.ofMinutes(15))
    clientTimeout.set(Duration.ofMinutes(15))
}
