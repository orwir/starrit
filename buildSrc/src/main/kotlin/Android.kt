import org.gradle.api.JavaVersion
import org.gradle.api.Project
import orwir.gradle.extension.gradleProperty

object Android {

    const val buildToolsVersion = "28.0.3"
    val javaVersion = JavaVersion.VERSION_1_8

    object Application {
        fun versionName(project: Project) = project.gradleProperty("versionName", "canary")
        fun versionCode(project: Project) = project.gradleProperty("versionCode", 5)
    }

    object Sdk {
        const val min = 21
        const val compile = 29
        const val target = compile
    }

}