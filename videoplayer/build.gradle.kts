plugins {
    id(Build.Plugin.androidLibrary)
    id(Build.Plugin.kotlinAndroid)
    id(Build.Plugin.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    buildToolsVersion = Build.Version.buildTools

    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")
}

dependencies {
    implementation(kotlin("stdlib-jdk8", Build.Version.kotlin))

    implementation(Library.AndroidX.core)
    implementation(Library.AndroidX.appCompat)
    implementation(Library.AndroidX.constraintLayout)
    implementation(Library.Coil.coil)
    implementation(Library.exoPlayer)
}