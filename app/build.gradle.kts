plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("androidx.navigation.safeargs")
    id("com.google.devtools.ksp")
    id ("kotlin-parcelize")

}

android {
    namespace = "com.panther.events_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.panther.events_app"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.compose.ui:ui:1.5.1")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.2")
    implementation("androidx.compose.ui:ui-tooling-preview-android:1.5.1")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.tracing:tracing-perfetto-handshake:1.0.0-rc01")
    implementation("com.google.android.gms:play-services-base:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Compose
    implementation("androidx.compose.ui:ui:1.5.1")

    implementation("androidx.compose.runtime:runtime:1.5.1")

    val nav_version = "2.6.0"
    implementation("androidx.navigation:navigation-compose:2.7.3")


    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Coil
    implementation("io.coil-kt:coil:2.2.2")

    implementation ("com.google.android.gms:play-services-auth:20.7.0")

}