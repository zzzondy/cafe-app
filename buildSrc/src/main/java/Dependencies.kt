object Dependencies {
    object Compose {
        const val composeVersion = "1.4.2"
        private const val composeBOMVersion = "2023.01.00"

        // UI
        const val bom = "androidx.compose:compose-bom:$composeBOMVersion"
        const val ui = "androidx.compose.ui:ui"
        const val material = "androidx.compose.material3:material3:1.1.0-alpha06"

        // Tooling
        const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val composeTooling = "androidx.compose.ui:ui-tooling"
        const val testManifest = "androidx.compose.ui:ui-test-manifest"

        // Constraint layout
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:1.0.1"

        // Splash screen
        const val splashScreen = "androidx.core:core-splashscreen:1.0.0"
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
        const val junit = "junit:junit:4.13.2"
        const val androidJUnit = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        const val composeTests = "androidx.compose.ui:ui-test-junit4:1.2.1"
    }

    object ComposeDestinations {
        private const val composeDestinationsVersion = "1.8.33-beta"

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
        const val bom = "com.google.firebase:firebase-bom:31.1.1"

        const val firestore = "com.google.firebase:firebase-firestore-ktx"
        const val authentication = "com.google.firebase:firebase-auth-ktx"
        const val storage = "com.google.firebase:firebase-storage-ktx"
    }

    object Hilt {
        private const val hiltVersion = "2.44"

        const val daggerHiltProject = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"

        const val hilt = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val hiltCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"
    }

    object Landscapist {
        private const val landscapistVersion = "2.1.0"

        const val bom = "com.github.skydoves:landscapist-bom:$landscapistVersion"
        const val coil = "com.github.skydoves:landscapist-coil"
        const val animation = "com.github.skydoves:landscapist-animation"
    }

    object Accompanist {
        private const val accompanistVersion = "0.28.0"

        const val placeholder =
            "com.google.accompanist:accompanist-placeholder-material:$accompanistVersion"

        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion"
    }

    object Room {
        private const val roomVersion = "2.5.0"

        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
        const val ktx = "androidx.room:room-ktx:$roomVersion"
    }
}