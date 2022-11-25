plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.ksp) version Plugins.kspVersion
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
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Lifecycle
    implementation(Dependencies.Lifecycle.core)
    implementation(Dependencies.Lifecycle.lifecycleRuntime)
    implementation(Dependencies.Lifecycle.activity)

    // Ui
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)

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
    implementation(Dependencies.ComposeDestinations.core)
    ksp(Dependencies.ComposeDestinations.ksp)

    // Domain
    implementation(project(":domain"))

    // Data
    implementation(project(":data"))
}