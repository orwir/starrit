object Library {
    object Kotlin {
        const val std = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Build.kotlinVersion}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Build.kotlinVersion}"
    }

    object AndroidX {
        private const val versionLifecycle = "2.2.0-rc03"
        private const val versionNavigation = "2.1.0"
        private const val versionPreference = "1.1.0"

        const val core = "androidx.core:core-ktx:1.1.0"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:$versionLifecycle"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$versionLifecycle"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$versionLifecycle"
        const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:$versionLifecycle"
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val coordinator = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$versionNavigation"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$versionNavigation"
        const val paging = "androidx.paging:paging-runtime:2.1.1"
        const val browser = "androidx.browser:browser:1.2.0"
        const val preference = "androidx.preference:preference:$versionPreference"
        const val preferenceKtx = "androidx.preference:preference-ktx:$versionPreference"
    }

    object Koin {
        private const val version = "2.0.1"

        const val core = "org.koin:koin-core:$version"
        const val android = "org.koin:koin-android:$version"
        const val androidScope = "org.koin:koin-androidx-scope:$version"
        const val androidViewModel = "org.koin:koin-androidx-viewmodel:$version"
    }

    object Coil {
        private const val version = "0.8.0"

        const val coil = "io.coil-kt:coil:$version"
        const val base = "io.coil-kt:coil-base:$version"
        const val gif = "io.coil-kt:coil-gif:$version"
    }

    object Squareup {
        private const val versionOkhttp = "4.2.2"
        private const val versionMoshi = "1.9.2"
        private const val versionRetrofit = "2.6.3"

        const val okhttp = "com.squareup.okhttp3:okhttp:$versionOkhttp"
        const val logginInterceptor = "com.squareup.okhttp3:logging-interceptor:$versionOkhttp"
        const val moshi = "com.squareup.moshi:moshi:$versionMoshi"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$versionMoshi"
        const val moshiKotlinCodgen = "com.squareup.moshi:moshi-kotlin-codegen:$versionMoshi"
        const val retrofit = "com.squareup.retrofit2:retrofit:$versionRetrofit"
        const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:$versionRetrofit"
    }

    const val exoPlayer = "com.google.android.exoplayer:exoplayer:2.11.0"
    const val timber = "com.jakewharton.timber:timber:4.7.1"

    object Junit {
        private const val version = "5.5.2"

        const val api = "org.junit.jupiter:junit-jupiter-api:$version"
        const val engine = "org.junit.jupiter:junit-jupiter-engine:$version"
        const val params = "org.junit.jupiter:junit-jupiter-params:$version"
    }

    const val mockk = "io.mockk:mockk:1.9.3"
}