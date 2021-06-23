plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

extra.apply {
    set("PUBLISH_GROUP_ID", "io.github.drahovac")
    set("PUBLISH_VERSION", "0.0.2")
    set("PUBLISH_ARTIFACT_ID", "file-save")
}

apply(from = "${rootProject.projectDir}/scripts/publish-module.gradle")

android {
    compileSdkVersion(Versions.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.compileSdkVersion)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.5"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(KotlinDependencies.kotlin)
    implementation(KotlinDependencies.coroutines)
    implementation(AndroidDependencies.androidxCore)
    implementation(AndroidDependencies.appCompat)

    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.mockito)
    testImplementation(TestDependencies.mockitoKotlin)
    androidTestImplementation(TestDependencies.androidxTest)
    androidTestImplementation(TestDependencies.espresso)
}