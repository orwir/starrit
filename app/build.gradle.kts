plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Android.Sdk.compile)
    buildToolsVersion = Android.buildToolsVersion

    defaultConfig {
        applicationId = Android.Application.id
        versionCode = Android.Application.versionCode(project)
        versionName = Android.Application.versionName(project)

        minSdkVersion(Android.Sdk.min)
        targetSdkVersion(Android.Sdk.target)

        manifestPlaceholders = mapOf(
            "schema" to Android.Application.schema,
            "authorizationHost" to Android.Application.authorizationHost
        )
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

//    buildTypes {
//        getByName("release") {
//            isMinifyEnabled = true
//            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//        }
//    }

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
    implementation(project(":authorization"))
    implementation(project(":splash"))
    implementation(project(":feed"))
    implementation(Library.AndroidX.appcompat)
    implementation(Library.AndroidX.navigationFragment)
    implementation(Library.AndroidX.navigationUi)
    implementation(Library.AndroidX.browser)
}