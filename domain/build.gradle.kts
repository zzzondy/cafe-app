plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(Dependencies.Paging.common)

    testImplementation(Dependencies.Testing.junit)
    testImplementation(Dependencies.Testing.mockito)
    testImplementation(Dependencies.Testing.coroutines)
}