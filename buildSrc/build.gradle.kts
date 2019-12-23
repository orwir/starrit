plugins { `kotlin-dsl` }

repositories {
    jcenter()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:3.5.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
    implementation(gradleApi())
    implementation(localGroovy())
}