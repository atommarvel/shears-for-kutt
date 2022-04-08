object Dependencies {
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val `retrofit-kotlinx-serialization` =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    const val junit = "junit:junit:4.+"
    const val `android-plugin` = "com.android.tools.build:gradle:7.1.2"
    const val `versions-plugin` = "com.github.ben-manes:gradle-versions-plugin:+"

    object androidx {
        const val core = "androidx.core:core-ktx:1.7.0"
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val fragment = "androidx.fragment:fragment-ktx:1.4.1"
        const val `paging-compose` = "androidx.paging:paging-compose:1.0.0-alpha14"
        const val `hilt-navigation-compose` = "androidx.hilt:hilt-navigation-compose:1.0.0"
        const val navigation = "androidx.navigation:navigation-compose:2.4.2"

        object activity {
            const val version = "1.4.0"
            const val ktx = "androidx.activity:activity-ktx:$version"
            const val compose = "androidx.activity:activity-compose:$version"
        }

        object lifecycle {
            const val version = "2.4.1"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val `viewmodel-compose` =
                "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        }

        object compose {
            const val version = "1.1.1"
            const val ui = "androidx.compose.ui:ui:$version"
            const val `ui-tooling` = "androidx.compose.ui:ui-tooling:$version"
            const val material = "androidx.compose.material:material:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val `foundation-layout` = "androidx.compose.foundation:foundation-layout:$version"
            const val `material-icons-extended` =
                "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val `runtime-livedata` = "androidx.compose.runtime:runtime-livedata:$version"
            const val test = "androidx.compose.ui:ui-test-junit4:$version"
        }

        object test {
            const val junit = "androidx.test.ext:junit:1.1.3"
            const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        }
    }

    object google {
        const val material = "com.google.android.material:material:1.5.0"
        const val `play-oss-licenses` = "com.google.android.gms:play-services-oss-licenses:17.0.0"
        const val `oss-licenses-plugin` = "com.google.android.gms:oss-licenses-plugin:0.10.4"
        const val `services-plugin` = "com.google.gms:google-services:4.3.10"

        object accompanist {
            const val version = "0.23.1"
            const val pager = "com.google.accompanist:accompanist-pager:$version"
            const val `pager-indicators` =
                "com.google.accompanist:accompanist-pager-indicators:$version"
        }

        object firebase {
            const val bom = "com.google.firebase:firebase-bom:29.3.0"
            const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx:18.2.9"
            const val `crashlytics-plugin` = "com.google.firebase:firebase-crashlytics-gradle:2.8.1"
        }

        object play {
            const val core = "com.google.android.play:core:1.10.3"
            const val `core-ktx` = "com.google.android.play:core-ktx:1.8.1"
        }

        object dagger {
            const val version = "2.41"
            const val dagger = "com.google.dagger:dagger:$version"
            const val `dagger-compiler` = "com.google.dagger:dagger-compiler:$version"
            const val hilt = "com.google.dagger:hilt-android:$version"
            const val `hilt-compiler` = "com.google.dagger:hilt-compiler:$version"
            const val `hilt-testing` = "com.google.dagger:hilt-android-testing:$version"
            const val `hilt-plugin` = "com.google.dagger:hilt-android-gradle-plugin:$version"
        }
    }

    object jetbrains {
        object kotlin {
            // version should be compatible with compose: https://developer.android.com/jetpack/androidx/releases/compose-kotlin
            const val version = "1.6.10"
            const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        }

        object kotlinx {
            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1"
        }
    }
}