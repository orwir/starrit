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
        const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:$v_lifecycle"
        const val lifecycle_viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$v_lifecycle"
        const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$v_lifecycle"
        const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$v_lifecycle"

        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val fragment = "androidx.fragment:fragment:1.2.0-rc04"
        const val material = "com.google.android.material:material:1.1.0-rc01"
        const val recycler_view = "androidx.recyclerview:recyclerview:1.1.0"
        const val coordinator_layout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
        const val constraint_layout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val paging = "androidx.paging:paging-runtime:2.1.1"
        const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:$v_navigation"
        const val navigation_ui = "androidx.navigation:navigation-ui-ktx:$v_navigation"
        const val browser = "androidx.browser:browser:1.2.0"
    }

    object Koin {
        private const val version = "2.0.1"

        const val core = "org.koin:koin-core:$version"
        const val android = "org.koin:koin-android:$version"
        const val android_scope = "org.koin:koin-androidx-scope:$version"
        const val android_viewmodel = "org.koin:koin-androidx-viewmodel:$version"
    }

    object Coil {
        private const val version = "0.8.0"

        const val coil = "io.coil-kt:coil:$version"
        const val base = "io.coil-kt:coil-base:$version"
        const val gif = "io.coil-kt:coil-gif:$version"
    }

    object Squareup {
        private const val v_okhttp = "4.2.2"
        private const val v_moshi = "1.9.2"
        private const val v_retrofit = "2.6.3"

        const val okhttp = "com.squareup.okhttp3:okhttp:$v_okhttp"
        const val loggin_interceptor = "com.squareup.okhttp3:logging-interceptor:$v_okhttp"
        const val moshi = "com.squareup.moshi:moshi:$v_moshi"
        const val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:$v_moshi"
        const val moshi_kotlin_codgen = "com.squareup.moshi:moshi-kotlin-codegen:$v_moshi"
        const val retrofit = "com.squareup.retrofit2:retrofit:$v_retrofit"
        const val retrofit_converter_moshi = "com.squareup.retrofit2:converter-moshi:$v_retrofit"
    }

    const val exoplayer = "com.google.android.exoplayer:exoplayer:2.11.0"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
}