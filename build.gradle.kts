import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import java.util.*


plugins {
    kotlin("multiplatform") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
    id("com.android.library")
    id("maven-publish")
    id("signing")
}

group = "dev.antonius"
version = "0.1.2"

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
                    implementation(compose.runtime)
                    implementation(compose.foundation)
                    implementation(compose.ui)
                    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                    implementation(compose.material3)
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
        minSdk = 21
        targetSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

// Publish to mavenCentral
ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null

val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        ext[name.toString()] = value
    }
} else {
    ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
    ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
}


val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

fun getExtraString(name: String) = ext[name]?.toString()

publishing {
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = getExtraString("ossrhUsername")
                password = getExtraString("ossrhPassword")
            }
        }
    }

    publications.withType<MavenPublication> {
        groupId = group as String
        artifactId = project.name
        version = version

        artifact(javadocJar.get())

        pom {
            name.set("Chords")
            description.set("A small utility library to make Jetbrains/Jetpack Compose more harmonic.")
            url.set("https://github.com/antoniusnaumann/chords/tree/main")

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
            scm {
                connection.set("scm:git:git://github.com/antoniusnaumann/chords.git")
                developerConnection.set("scm:git:ssh://github.com:antoniusnaumann/chords.git")
                url.set("https://github.com/antoniusnaumann/chords/tree/main")
            }
        }
    }
}

signing {
    sign(publishing.publications)
}