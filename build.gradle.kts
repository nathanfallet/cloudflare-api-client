plugins {
    kotlin("multiplatform") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    id("convention.publication")
    id("org.jetbrains.kotlinx.kover") version "0.7.4"
    id("com.google.devtools.ksp") version "1.9.20-1.0.13"
}

group = "me.nathanfallet.cloudflare"
version = "4.3.0"

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
    jvm {
        jvmToolchain(19)
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

    val ktorxVersion = "2.3.0"
    val usecasesVersion = "1.6.0"

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
