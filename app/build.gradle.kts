plugins {
    id("com.android.application")
}

android {
    namespace = "com.jmaaix.testttttt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jmaaix.testttttt"
        minSdk = 24
        targetSdk = 33
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
    // CameraX core library
    implementation ("androidx.appcompat:appcompat:1.3.1")
    // CameraX lifecycle library
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.room:room-runtime:2.2.0")
    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation(files("libs/activation.jar"))
    implementation(files("libs/mail.jar"))
    implementation(files("libs/additionnal.jar"))
    annotationProcessor("androidx.room:room-compiler:2.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation ("at.favre.lib:bcrypt:0.9.0")


}