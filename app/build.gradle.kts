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
    implementation ("androidx.camera:camera-core:1.1.0")
    implementation ("androidx.camera:camera-camera2:1.1.0")
    implementation ("androidx.camera:camera-lifecycle:1.1.0")
    implementation ("androidx.camera:camera-view:1.0.0-alpha27")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.room:room-runtime:2.2.0")
    implementation ("androidx.core:core-ktx:1.7.0")
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.room:room-common:2.6.0")
    implementation("com.google.firebase:protolite-well-known-types:18.0.0")
    annotationProcessor("androidx.room:room-compiler:2.6.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation ("androidx.activity:activity:1.8.0")
    implementation ("androidx.fragment:fragment:1.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.6.2")
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