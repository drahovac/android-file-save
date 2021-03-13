object Versions {
    const val kotlinVersion = "1.4.0"
    const val androidPluginVersion = "4.0.1"
    const val androidxCoreVersion = "1.3.2"
    const val appCompatVersion = "1.2.0"

    const val junitVersion = "4.12"
    const val androidxTestVersion = "1.1.2"
    const val espressoVersion = "3.3.0"
    const val activity_version = "1.2.1"
}

object KotlinDependencies {
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
}

object AndroidDependencies {
    const val plugin = "com.android.tools.build:gradle:${Versions.androidPluginVersion}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.androidxCoreVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val activity = "androidx.activity:activity-ktx:${Versions.activity_version}"
}

object TestDependencies {
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val androidxTest = "androidx.test.ext:junit:${Versions.androidxTestVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
}