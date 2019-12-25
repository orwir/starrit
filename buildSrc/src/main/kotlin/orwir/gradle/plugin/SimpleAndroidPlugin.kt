package orwir.gradle.plugin

import Android
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class SimpleAndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.defaultConfig()
    }

    private fun Project.defaultConfig() {
        extensions.getByType<BaseExtension>().apply {
            compileSdkVersion(Android.Sdk.compile)
            buildToolsVersion(Android.buildToolsVersion)

            defaultConfig {
                minSdkVersion(Android.Sdk.min)
                targetSdkVersion(Android.Sdk.target)
            }

            sourceSets {
                asMap.forEach { (name, source) ->
                    source.java.srcDir("src/$name/kotlin")
                }
            }

            compileOptions {
                sourceCompatibility = Android.javaVersion
                targetCompatibility = Android.javaVersion
            }

            testOptions {
                unitTests.isIncludeAndroidResources = true
            }

            packagingOptions {
                exclude("META-INF/LICENSE*")
            }

            tasks.withType(KotlinCompile::class.java).all {
                kotlinOptions {
                    jvmTarget = Android.javaVersion.toString()
                }
            }

            silentConfigure<AndroidExtensionsExtension> {
                isExperimental = true
            }

            silentConfigure<KaptExtension> {
                correctErrorTypes = true
                useBuildCache = true
            }
        }
    }

    // TODO: find proper way to configure optional extensions
    private inline fun <reified T : Any> Project.silentConfigure(noinline configuration: T.() -> Unit) {
        try {
            configure<T> { configuration() }
        } catch (skip: Throwable) {
            // just ignored because there is no such extension in the module
        }
    }

}