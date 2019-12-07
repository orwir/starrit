plugins {
    id(Build.Plugin.androidApplication)
    id(Build.Plugin.kotlinAndroid)
    id(Build.Plugin.kotlinAndroidExtensions)
    id(Build.Plugin.kotlinKapt)
    id(Build.Plugin.navigationKotlinSafeArgs)
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
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
    implementation(Library.Kotlin.std)
    implementation(Library.Kotlin.coroutines)

    implementation(Library.AndroidX.core)
    implementation(Library.AndroidX.lifecycleExtensions)
    implementation(Library.AndroidX.lifecycleViewModel)
    implementation(Library.AndroidX.lifecycleRuntime)
    implementation(Library.AndroidX.lifecycleLivedata)
    implementation(Library.AndroidX.appCompat)
    implementation(Library.AndroidX.constraintLayout)
    implementation(Library.AndroidX.navigationFragment)
    implementation(Library.AndroidX.navigationUi)
    implementation(Library.AndroidX.paging)
    implementation(Library.AndroidX.browser)

    implementation(Library.Koin.core)
    implementation(Library.Koin.android)
    implementation(Library.Koin.androidScope)
    implementation(Library.Koin.androidViewModel)

    implementation(Library.Coil.coil)
    implementation(Library.Coil.gif)

    implementation(Library.Squareup.okhttp)
    implementation(Library.Squareup.logginInterceptor)
    implementation(Library.Squareup.moshi)
    implementation(Library.Squareup.moshiKotlin)
    kapt(Library.Squareup.moshiKotlinCodgen)
    implementation(Library.Squareup.retrofit)
    implementation(Library.Squareup.retrofitMoshiConverter)

    implementation(Library.exoPlayer)
}