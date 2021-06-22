// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

apply(from = "${rootDir}/scripts/publish-root.gradle")

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(AndroidDependencies.plugin)
        classpath(KotlinDependencies.kotlinPlugin)
        classpath(KotlinDependencies.detekt)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    apply(from = "$rootDir/detekt.gradle")
}