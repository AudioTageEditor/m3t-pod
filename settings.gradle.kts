// File: settings.gradle.kts
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
            // JitPack hanya dipakai untuk library com.github.*
            content { includeGroupByRegex("com\\.github\\..*") }
        }
    }
}

rootProject.name = "TagBarDroid"
include(":app")
