@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "dev.amal.movieapp.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "dev.amal.movieapp.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.activity:activity-compose:1.7.0")

    // Jetpack Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // Accompanist System UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.0-alpha")

    // Material 3
    implementation("androidx.compose.material3:material3:1.1.0-beta02")

    // Http Client
    implementation("io.ktor:ktor-client-okhttp:2.2.4")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.0")

    // Paging 3.0
    implementation("androidx.paging:paging-compose:1.0.0-alpha18")

    // Dagger Hilt
    val hiltVersion = "2.45"
    val hiltCompilerVersion = "1.0.0"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    kapt("androidx.hilt:hilt-compiler:$hiltCompilerVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltCompilerVersion")
}