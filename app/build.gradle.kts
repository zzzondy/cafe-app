plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.ksp) version Plugins.kspVersion
    id(Plugins.kapt)
    id(Plugins.daggerHilt)
    id(Plugins.gms)
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

android {
    namespace = "com.cafeapp"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.composeVersion
    }
    packagingOptions {
        resources {
            excludes += "META-INF/**"
        }
    }
}

dependencies {

    // Lifecycle
    implementation(Dependencies.Lifecycle.core)
    implementation(Dependencies.Lifecycle.lifecycleRuntime)
    implementation(Dependencies.Lifecycle.activity)

    // Compose BOM
    implementation(platform(Dependencies.Compose.bom))
    androidTestImplementation(platform(Dependencies.Compose.bom))

    // Ui
    implementation(Dependencies.Compose.ui)
    implementation("androidx.compose.material3:material3:1.1.0-alpha06")
    implementation(Dependencies.Compose.constraintLayout)

    // Accompanist
    implementation(Dependencies.Accompanist.placeholder)
    implementation(Dependencies.Accompanist.systemUiController)

    // Compose tooling
    implementation(Dependencies.Compose.composeTooling)
    debugImplementation(Dependencies.Compose.composeToolingPreview)
    debugImplementation(Dependencies.Compose.testManifest)

    // Testing
    testImplementation(Dependencies.Testing.junit)
    androidTestImplementation(Dependencies.Testing.androidJUnit)
    androidTestImplementation(Dependencies.Testing.espresso)
    androidTestImplementation(Dependencies.Testing.composeTests)

    // Compose Destinations
    implementation(Dependencies.ComposeDestinations.animationsCore)
    ksp(Dependencies.ComposeDestinations.ksp)

    // Hilt
    implementation(Dependencies.Hilt.hilt)
    implementation(Dependencies.Hilt.hiltCompose)
    kapt(Dependencies.Hilt.hiltAndroidCompiler)
    kapt(Dependencies.Hilt.hiltCompiler)

    // Firebase
    implementation(platform(Dependencies.Firebase.bom))
    implementation(Dependencies.Firebase.firestore)
    implementation(Dependencies.Firebase.authentication)
    implementation(Dependencies.Firebase.storage)

    // Paging
    implementation(Dependencies.Paging.compose)

    // Landscapist
    implementation(Dependencies.Landscapist.bom)
    implementation(Dependencies.Landscapist.coil)
    implementation(Dependencies.Landscapist.animation)

    // Splash screen
    implementation(Dependencies.Compose.splashScreen)

    // Domain
    implementation(project(Modules.domain))

    // Data
    implementation(project(Modules.data))


}