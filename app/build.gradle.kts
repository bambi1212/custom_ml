plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.custom_ml"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.custom_ml"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    //added by me
    aaptOptions {
        noCompress ("WasteClassificationModel.tflite");
        // or noCompress "lite"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.image.labeling.custom.common)
    implementation(libs.camera.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //image classification
    implementation("com.google.mlkit:image-labeling-custom:17.0.2")


}