// Top-level build file where you can add configuration options common to all sub-projects/modules.

//plugins {
//    id("com.android.application") version "8.1.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
//    alias(libs.plugins.androidx.navigation.safe.args) apply false
//}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}

//buildscript {
//    val nav_version by extra("2.5.0")
//    dependencies {
//        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
//        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48")
//    }
//}