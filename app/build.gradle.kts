plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services) // ✅ Correct plugin usage
}

android {
    namespace = "com.example.toda"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.toda"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Android UI Libraries
    implementation(libs.appcompat)
    implementation(libs.material)

    // ✅ Use Firebase BoM (This auto-manages Firebase versions correctly)
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))

    // ✅ Firebase Dependencies (No need to specify version numbers)
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")

    // ✅ Google Maps + GPS
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
