buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Google.gms)
        classpath(Dependencies.Hilt.daggerHiltProject)
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id(Plugins.androidApplication) version Plugins.androidApplicationVersion apply false
    id(Plugins.androidLibrary) version Plugins.androidLibraryVersion apply false
    id(Plugins.kotlinAndroid) version Plugins.kotlinVersion apply false
    id(Plugins.kotlinJvm) version Plugins.kotlinVersion apply false
    id(Plugins.androidTest) version Plugins.androidTestVersion apply false
}