// File: app/build.gradle.kts
plugins {
    // Versi plugin ada di build.gradle.kts root.
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.tagbardroid.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.tagbardroid.app"
        // JAudioTagger memakai java.util.Base64, yang baru tersedia mulai API 26.
        minSdk = 26
        targetSdk = 35
        versionCode = 12
        versionName = "0.5.0"
    }

    // Kunci tetap: APK dari setiap build bertanda tangan sama, sehingga bisa langsung
    // dipasang sebagai pembaruan. Jangan ganti atau hapus debug.keystore.
    signingConfigs {
        getByName("debug") {
            storeFile = file("debug.keystore")
            storeType = "pkcs12"
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            // JAudioTagger memakai refleksi dan mereferensikan kelas desktop (java.awt),
            // jadi R8 dimatikan sampai aturan ProGuard-nya disiapkan.
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "/META-INF/DEPENDENCIES"
            )
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    // Tag editor (Java murni, JitPack, tanpa NDK). Lisensi LGPL.
    implementation("com.github.Adonai:jaudiotagger:2.3.15")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // Pemutar audio: Media3 ExoPlayer (keluaran float untuk audio Hi-Res 24-bit / 96 kHz)
    implementation("androidx.media3:media3-exoplayer:1.8.0")

    // AndroidX
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2025.09.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    debugImplementation("androidx.compose.ui:ui-tooling")

    testImplementation("junit:junit:4.13.2")
}
