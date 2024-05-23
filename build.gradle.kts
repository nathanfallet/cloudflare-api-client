plugins {
    kotlin("multiplatform") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("convention.publication")
    id("org.jetbrains.kotlinx.kover") version "0.8.0"
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
}

group = "me.nathanfallet.cloudflare"
version = "4.3.2"

repositories {
    mavenCentral()
}

kotlin {
    // Tiers are in accordance with <https://kotlinlang.org/docs/native-target-support.html>
    // Tier 1
    macosX64()
    macosArm64()
    iosSimulatorArm64()
    iosX64()

    // Tier 2
    linuxX64()
    linuxArm64()
    watchosSimulatorArm64()
    watchosX64()
    watchosArm32()
    watchosArm64()
    tvosSimulatorArm64()
    tvosX64()
    tvosArm64()
    iosArm64()

    // Tier 3
    mingwX64()
    //watchosDeviceArm64() // Not supported by ktor

    // jvm & js
    jvmToolchain(21)
    jvm {
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }
    js {
        binaries.library()
        nodejs()
        browser()
        //generateTypeScriptDefinitions() // Not supported for now because of collections etc...
    }

    applyDefaultHierarchyTemplate()

    val ktorxVersion = "2.3.2"
    val usecasesVersion = "1.6.1"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

                api("me.nathanfallet.usecases:usecases:$usecasesVersion")
                api("me.nathanfallet.ktorx:ktor-routers-client:$ktorxVersion")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-serialization-kotlinx-xml:$ktorxVersion")
                implementation("uk.co.lucasweb:aws-v4-signer-java:1.3")
            }
        }
    }
}
