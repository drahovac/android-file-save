plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.chicco.sample"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.compileSdkVersion)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":filesave"))
    implementation(KotlinDependencies.kotlin)
    implementation(AndroidDependencies.androidxCore)
    implementation(AndroidDependencies.appCompat)
    implementation(AndroidDependencies.activity)

    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.androidxTest)
    androidTestImplementation(TestDependencies.espresso)
}
