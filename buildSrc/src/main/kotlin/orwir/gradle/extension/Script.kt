package orwir.gradle.extension

import com.android.build.gradle.internal.dsl.DefaultConfig

fun DefaultConfig.stringField(name: String, value: String) {
    buildConfigField("String", name, "\"$value\"")
}