plugins {
    id(Build.Plugin.androidLibrary)
    id(Build.Plugin.kotlinAndroid)
    id(Build.Plugin.kotlinAndroidExtensions)
    id(Build.Plugin.kotlinKapt)
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
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)

        buildConfigField("String", "REDDIT_BASE_URL", "\"https://www.reddit.com\"")
        buildConfigField("String", "REDDIT_AUTH_URL", "\"https://oauth.reddit.com\"")
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

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