object Library {

    object Kotlin {
        const val std = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Build.kotlinVersion}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Build.kotlinVersion}"
    }

    object AndroidX {
        private const val v_lifecycle = "2.2.0-rc03"
        private const val v_navigation = "2.1.0"

        const val core = "androidx.core:core-ktx:1.1.0"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:$v_lifecycle"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$v_lifecycle"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$v_lifecycle"
        const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:$v_lifecycle"

        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val fragment = "androidx.fragment:fragment:1.2.0-rc04"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val paging = "androidx.paging:paging-runtime:2.1.1"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$v_navigation"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$v_navigation"
        const val browser = "androidx.browser:browser:1.2.0"
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

    const val junit = "junit:junit:4.12"
    const val truth = "com.google.truth:truth:1.0"
    const val mockk = "io.mockk:mockk:1.9.3"
    const val robolectric = "org.robolectric:robolectric:4.3"

    /*
    // Core library
      androidTestImplementation 'androidx.test:core:1.0.0'

      // AndroidJUnitRunner and JUnit Rules
      androidTestImplementation 'androidx.test:runner:1.1.0'
      androidTestImplementation 'androidx.test:rules:1.1.0'

      // Assertions
      androidTestImplementation 'androidx.test.ext:junit:1.0.0'
      androidTestImplementation 'androidx.test.ext:truth:1.0.0'
      androidTestImplementation 'com.google.truth:truth:0.42'

      // Espresso dependencies
      androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
      androidTestImplementation 'androidx.test.espresso:espresso-contrib:3.2.0'
      androidTestImplementation 'androidx.test.espresso:espresso-intents:3.2.0'
      androidTestImplementation 'androidx.test.espresso:espresso-accessibility:3.2.0'
      androidTestImplementation 'androidx.test.espresso:espresso-web:3.2.0'
      androidTestImplementation 'androidx.test.espresso.idling:idling-concurrent:3.2.0'

      // The following Espresso dependency can be either "implementation"
      // or "androidTestImplementation", depending on whether you want the
      // dependency to appear on your APK's compile classpath or the test APK
      // classpath.
      androidTestImplementation 'androidx.test.espresso:espresso-idling-resource:3.2.0'
     */
}