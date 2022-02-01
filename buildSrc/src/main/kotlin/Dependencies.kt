object Dependencies {
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val `retrofit-kotlinx-serialization` =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    const val junit = "junit:junit:4.+"

    object androidx {
        const val core = "androidx.core:core-ktx:1.7.0"
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val fragment = "androidx.fragment:fragment-ktx:1.4.0" // 1.4.1 update
        const val activity = "androidx.activity:activity-ktx:1.4.0"
        const val `activity-compose` = "androidx.activity:activity-compose:1.4.0"
        const val `paging-compose` = "androidx.paging:paging-compose:1.0.0-alpha14"
        const val `hilt-navigation-compose` = "androidx.hilt:hilt-navigation-compose:1.0.0"
        const val navigation = "androidx.navigation:navigation-compose:2.4.0"

        object lifecycle {
            const val version = "2.4.0"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val `viewmodel-compose` =
                "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        }

        object compose {
            const val version = "1.0.5"
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

        object accompanist {
            const val version = "0.13.0"
            const val pager = "com.google.accompanist:accompanist-pager:$version"
            const val `pager-indicators` =
                "com.google.accompanist:accompanist-pager-indicators:$version"
        }

        object firebase {
            const val bom = "com.google.firebase:firebase-bom:28.2.0"
            const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx:18.2.7"
        }

        object play {
            const val core = "com.google.android.play:core:1.10.3"
            const val `core-ktx` = "com.google.android.play:core-ktx:1.8.1"
        }

        object dagger {
            const val version = "2.40.5"
            const val dagger = "com.google.dagger:dagger:$version"
            const val `dagger-compiler` = "com.google.dagger:dagger-compiler:$version"
            const val hilt = "com.google.dagger:hilt-android:$version"
            const val `hilt-compiler` = "com.google.dagger:hilt-compiler:$version"
            const val `hilt-testing` = "com.google.dagger:hilt-android-testing:$version"
        }
    }

    object jetbrains {
        object kotlinx {
            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1"
            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0"
        }
    }
}