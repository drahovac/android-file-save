// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(AndroidDependencies.plugin)
        classpath(KotlinDependencies.kotlinPlugin)
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.16.0")

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

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}