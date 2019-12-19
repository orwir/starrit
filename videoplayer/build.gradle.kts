plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Android.Sdk.compile)
    buildToolsVersion = Android.buildToolsVersion

    defaultConfig {
        minSdkVersion(Android.Sdk.min)
        targetSdkVersion(Android.Sdk.target)
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

    compileOptions {
        sourceCompatibility = Android.javaVersion
        targetCompatibility = Android.javaVersion
    }

    kotlinOptions {
        jvmTarget = Android.javaVersion.toString()
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    api(Library.exoPlayer)

    implementation(Library.Kotlin.std)
    implementation(Library.AndroidX.core)
    implementation(Library.AndroidX.appcompat)
    implementation(Library.AndroidX.constraintLayout)
    implementation(Library.Coil.coil)
}