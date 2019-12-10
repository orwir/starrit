import com.android.build.gradle.internal.dsl.DefaultConfig

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Android.Sdk.compile)
    buildToolsVersion = Android.buildToolsVersion

    defaultConfig {
        minSdkVersion(Android.Sdk.min)
        targetSdkVersion(Android.Sdk.target)

        stringField("REDDIT_BASE_URL", "https://www.reddit.com")
        stringField("REDDIT_AUTH_URL", "https://oauth.reddit.com")
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

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

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

androidExtensions {
    isExperimental = true
}

dependencies {
    api(Library.Kotlin.std)
    api(Library.Kotlin.coroutines)
    api(Library.AndroidX.core)
    api(Library.AndroidX.appCompat)
    api(Library.AndroidX.lifecycleRuntime)
    api(Library.AndroidX.lifecycleLivedata)
    api(Library.AndroidX.lifecycleExtensions)
    api(Library.AndroidX.lifecycleViewModel)
    api(Library.Koin.core)
    api(Library.Koin.android)
    api(Library.Koin.androidScope)
    api(Library.Koin.androidViewModel)
    api(Library.Coil.coil)
    api(Library.Coil.gif)
    api(Library.timber)

    implementation(Library.Squareup.okhttp)
    implementation(Library.Squareup.logginInterceptor)
    implementation(Library.Squareup.moshi)
    implementation(Library.Squareup.moshiKotlin)
    kapt(Library.Squareup.moshiKotlinCodgen)
    implementation(Library.Squareup.retrofit)
    implementation(Library.Squareup.retrofitMoshiConverter)
}

fun DefaultConfig.stringField(name: String, value: String) {
    buildConfigField("String", name, "\"$value\"")
}