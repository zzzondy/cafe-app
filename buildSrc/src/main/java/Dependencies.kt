object Dependencies {
    object Compose {
        const val composeVersion = "1.3.0"

        // UI
        const val ui = "androidx.compose.ui:ui:$composeVersion"
        const val material = "androidx.compose.material3:material3:1.1.0-alpha02"

        // Tooling
        const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
        const val composeTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
        const val testManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"

        // Constraint layout
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
    }

    object Lifecycle {
        private const val coreVersion = "1.9.0"
        private const val activityVersion = "1.6.1"
        private const val lifecycleRuntimeVersion = "2.5.1"

        const val core = "androidx.core:core-ktx:$coreVersion"
        const val activity = "androidx.activity:activity-compose:$activityVersion"
        const val lifecycleRuntime =
            "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeVersion"
    }

    object Testing {
        const val junit = "org.junit.jupiter:junit-jupiter:5.9.1"
        const val androidJUnit = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        const val composeTests = "androidx.compose.ui:ui-test-junit4:1.2.1"
        const val mockito = "org.mockito.kotlin:mockito-kotlin:4.1.0"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
    }

    object ComposeDestinations {
        private const val composeDestinationsVersion = "1.7.27-beta"

        const val core = "io.github.raamcosta.compose-destinations:core:$composeDestinationsVersion"
        const val ksp = "io.github.raamcosta.compose-destinations:ksp:$composeDestinationsVersion"
        const val animationsCore =
            "io.github.raamcosta.compose-destinations:animations-core:$composeDestinationsVersion"
    }

    object Paging {
        private const val pagingVersion = "3.1.1"
        const val common = "androidx.paging:paging-common-ktx:$pagingVersion"
        const val compose = "androidx.paging:paging-compose:1.0.0-alpha17"
    }

    object Google {
        const val gms = "com.google.gms:google-services:4.3.14"
    }

    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:31.0.2"

        const val firestore = "com.google.firebase:firebase-firestore-ktx"
        const val authentication = "com.google.firebase:firebase-auth-ktx"
    }

    object Hilt {
        private const val hiltVersion = "2.44"

        const val hilt = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val hiltCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"
    }

    object Coil {
        const val compose = "io.coil-kt:coil-compose:2.2.2"
    }

    object Accompanist {
        private const val accompanistVersion = "0.28.0"

        const val placeholder =
            "com.google.accompanist:accompanist-placeholder-material:$accompanistVersion"

        const val flowLayouts = "com.google.accompanist:accompanist-flowlayout:$accompanistVersion"
    }

    object Coroutines {
        const val firebaseCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1"
    }
}