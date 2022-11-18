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
    }

    object Lifecycle {
        private const val coreVersion = "1.9.0"
        private const val activityVersion = "1.6.1"
        private const val lifecycleRuntimeVersion = "2.5.1"

        const val core = "androidx.core:core-ktx:$coreVersion"
        const val activity = "androidx.activity:activity-compose:$activityVersion"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeVersion"
    }

    object Testing {
        const val junit = "junit:junit:4.13.2"
        const val androidJUnit = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        const val composeTests = "androidx.compose.ui:ui-test-junit4:1.2.1"
    }
}