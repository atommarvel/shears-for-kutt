// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.`android-plugin`)
        classpath(Dependencies.jetbrains.kotlin.plugin)

        //firebase
        classpath(Dependencies.google.`services-plugin`)
        classpath(Dependencies.google.firebase.`crashlytics-plugin`)
        classpath(Dependencies.google.`oss-licenses-plugin`)

        //dagger
        classpath(Dependencies.google.dagger.`hilt-plugin`)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}