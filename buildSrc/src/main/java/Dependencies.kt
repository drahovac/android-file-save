object Versions {
    const val compileSdkVersion = 30
    const val minSdkVersion = "23"
    const val kotlinVersion = "1.5.10"
    const val detektVersion = "1.16.0"
    const val androidPluginVersion = "4.0.1"
    const val androidxCoreVersion = "1.5.0-beta03"
    const val appCompatVersion = "1.2.0"

    const val junitVersion = "4.12"
    const val mockitoVersion = "3.1.0"
    const val mockitoKotlinVersion = "1.5.0"
    const val androidxTestVersion = "1.1.2"
    const val espressoVersion = "3.3.0"
    const val activityVersion = "1.2.1"
    const val coroutinesVersion = "1.4.3"
}

object KotlinDependencies {
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detektVersion}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
}

object AndroidDependencies {
    const val plugin = "com.android.tools.build:gradle:${Versions.androidPluginVersion}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.androidxCoreVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val activity = "androidx.activity:activity-ktx:${Versions.activityVersion}"
}

object TestDependencies {
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockitoVersion}"
    const val mockitoKotlin = "com.nhaarman:mockito-kotlin-kt1.1:${Versions.mockitoKotlinVersion}"
    const val androidxTest = "androidx.test.ext:junit:${Versions.androidxTestVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
}