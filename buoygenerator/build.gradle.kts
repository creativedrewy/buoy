
plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
    kotlin("jvm")
}

group = "com.solanamobile.buoy"
version = "0.1.0"

repositories {
    mavenCentral()
    google()
    jcenter()
}

gradlePlugin {
    plugins {
        create("buoyPlugin") {
            id = "buoy-plugin"
            implementationClass = "com.solanamobile.buoy.GeneratorPlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")

    implementation("com.android.tools.build:gradle:7.2.1")
    implementation("com.squareup:kotlinpoet:1.12.0")
    implementation("com.google.code.gson:gson:2.9.0")

    testImplementation("junit:junit:4.13")
    testImplementation(gradleApi())
}

publishing {
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("build/repository")
        }
        mavenLocal()
    }
}