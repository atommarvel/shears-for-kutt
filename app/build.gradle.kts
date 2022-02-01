plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.0"
    //firebase
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.android.gms.oss-licenses-plugin")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.radiantmood.shears"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables  { // kts alternative?
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.5"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    //androidx
    implementation(Dependencies.androidx.core)
    implementation(Dependencies.androidx.appcompat)
    implementation(Dependencies.androidx.activity)
    implementation(Dependencies.androidx.lifecycle.runtime)
    implementation(Dependencies.androidx.lifecycle.livedata)
    implementation(Dependencies.androidx.lifecycle.viewmodel)
    implementation(Dependencies.androidx.fragment)

    //compose
    implementation(Dependencies.androidx.compose.ui)
    implementation(Dependencies.androidx.compose.`ui-tooling`)
    implementation(Dependencies.androidx.compose.material)
    implementation(Dependencies.androidx.compose.foundation)
    implementation(Dependencies.androidx.compose.`foundation-layout`)
    implementation(Dependencies.androidx.compose.`material-icons-extended`)
    implementation(Dependencies.androidx.compose.runtime)
    implementation(Dependencies.androidx.compose.`runtime-livedata`)
    implementation(Dependencies.google.accompanist.pager)
    implementation(Dependencies.google.accompanist.`pager-indicators`)

    //compose adjacent
    implementation(Dependencies.androidx.`activity-compose`)
    implementation(Dependencies.androidx.lifecycle.`viewmodel-compose`)
    implementation(Dependencies.androidx.lifecycle.`viewmodel-compose`)
    implementation(Dependencies.androidx.navigation)
    implementation(Dependencies.androidx.`paging-compose`)
    implementation(Dependencies.androidx.`hilt-navigation-compose`)

    //firebase
    implementation(platform(Dependencies.google.firebase.bom))
    implementation(Dependencies.google.firebase.crashlytics)

    //google
    implementation(Dependencies.google.material)
    implementation(Dependencies.google.play.core)
    implementation(Dependencies.google.play.`core-ktx`)

    //dagger
    implementation(Dependencies.google.dagger.dagger)
    kapt(Dependencies.google.dagger.`dagger-compiler`)
    //hilt
    implementation(Dependencies.google.dagger.hilt)
    kapt(Dependencies.google.dagger.`hilt-compiler`)
    //hilt - testing
    androidTestImplementation(Dependencies.google.dagger.`hilt-testing`)
    kaptAndroidTest(Dependencies.google.dagger.`hilt-compiler`)
    testImplementation(Dependencies.google.dagger.`hilt-testing`)
    kaptTest(Dependencies.google.dagger.`hilt-compiler`)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.`retrofit-kotlinx-serialization`)

    implementation(Dependencies.jetbrains.kotlinx.serialization)
    implementation(Dependencies.jetbrains.kotlinx.coroutines)

    implementation(Dependencies.google.`play-oss-licenses`)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidx.test.junit)
    androidTestImplementation(Dependencies.androidx.test.espresso)
    androidTestImplementation(Dependencies.androidx.compose.test)
}