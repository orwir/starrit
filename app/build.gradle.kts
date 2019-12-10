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
            "schema" to "gazzit",
            "host" to "oauth2"
        )

        buildConfigField("String", "SCHEMA", "\"gazzit\"")
        buildConfigField("String", "HOST", "\"oauth2\"")
        buildConfigField("String", "GAZZIT_CLIENT_ID", "\"${System.getenv("GAZZIT_CLIENT_ID")}\"")
        buildConfigField("String", "GAZZIT_CREDENTIALS_B64", "\"${System.getenv("GAZZIT_CREDENTIALS_B64")}\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

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
    implementation(Library.AndroidX.navigationFragment)
    implementation(Library.AndroidX.navigationUi)
    implementation(Library.AndroidX.paging)
    implementation(Library.AndroidX.browser)
}