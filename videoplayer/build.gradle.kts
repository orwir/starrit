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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(Library.exoPlayer)

    implementation(kotlin("stdlib-jdk8", Build.Version.kotlin))
    implementation(Library.AndroidX.core)
    implementation(Library.AndroidX.appCompat)
    implementation(Library.AndroidX.constraintLayout)
    implementation(Library.Coil.coil)
}