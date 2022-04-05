plugins {
    kotlin("multiplatform") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
    id("com.android.library")
    id("maven-publish")
}

group = "dev.antonius"
version = "0.1.0"

repositories {
    mavenCentral()
    google()
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                dependencies {
                    api(compose.runtime)
                    api(compose.foundation)
                    api(compose.ui)
                    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                    api(compose.material3)
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val desktopMain by getting

        val androidAndroidTestRelease by getting {
            androidTest.dependsOn(this)
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group as String
            artifactId = project.name
            version = version

            from(components["kotlin"])

            pom {
                name.set("Chords")
                description.set("A small utility library to make Jetbrains/Jetpack Compose more harmonic.")
                // url.set("")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("antoniusnaumann")
                        name.set("Antonius Naumann")
                        email.set("hi@antonius.dev")
                    }
                }
            }
        }
    }
}