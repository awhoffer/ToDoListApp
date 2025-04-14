plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.safe.args)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.todolist"
    compileSdk = 35
    buildFeatures{
        dataBinding = true
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.example.todolist"
        minSdk = 24
        targetSdk = 35
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Views/Fragments integration
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    //RxJava 3
    implementation (libs.rxandroid)
    implementation (libs.rxjava)
    //Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room)
    //Hilt
    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.android.compiler)


}