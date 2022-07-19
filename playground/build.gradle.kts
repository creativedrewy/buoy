import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
}

group = "com.solanamobile"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("com.squareup:kotlinpoet:1.12.0")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("com.solanamobile.buoy.playground.MainKt")
}