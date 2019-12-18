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
    sourceSets["test"].java.srcDir("src/test/kotlin")

    compileOptions {
        sourceCompatibility = Android.javaVersion
        targetCompatibility = Android.javaVersion
    }

    kotlinOptions {
        jvmTarget = Android.javaVersion.toString()
    }

    dataBinding {
        isEnabled = true
    }

}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":common"))
    implementation(project(":videoplayer"))
    implementation(Library.AndroidX.constraintLayout)
    implementation(Library.AndroidX.paging)
    implementation(Library.Squareup.retrofit)
}