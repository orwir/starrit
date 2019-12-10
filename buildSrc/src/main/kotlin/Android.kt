import org.gradle.api.JavaVersion
import org.gradle.api.Project

object Android {

    const val buildToolsVersion = "28.0.3"
    val javaVersion = JavaVersion.VERSION_1_8

    object Application {
        const val id = "orwir.gazzit"
        fun versionName(project: Project) = project.gradleProperty("versionName", "1.0.0")
        fun versionCode(project: Project) = project.gradleProperty("versionCode", 1)
    }

    object Sdk {
        const val min = 23
        const val compile = 29
        const val target = compile
    }

}