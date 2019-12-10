plugins {
    id(Build.Plugin.androidApplication)
    id(Build.Plugin.kotlinAndroid)
    id(Build.Plugin.kotlinAndroidExtensions)
    id(Build.Plugin.navigationKotlinSafeArgs)
}

androidExtensions {
    isExperimental = true
}

android {
    compileSdkVersion(AndroidSdk.compile)
    buildToolsVersion = Build.Version.buildTools

    defaultConfig {
        applicationId = "orwir.gazzit"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    dataBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8", Build.Version.kotlin))
    implementation(Library.Kotlin.coroutines)

    implementation(project(":common"))
    implementation(project(":videoplayer"))

    implementation(Library.AndroidX.constraintLayout)
    implementation(Library.AndroidX.navigationFragment)
    implementation(Library.AndroidX.navigationUi)
    implementation(Library.AndroidX.paging)
    implementation(Library.AndroidX.browser)
}