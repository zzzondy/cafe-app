buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Google.gms)
        classpath(Plugins.daggerHiltProject)
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
    id("com.android.test") version "7.4.0" apply false
}