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

//abstract class GeneratePluginVersion : DefaultTask() {
//    @get:org.gradle.api.tasks.Input
//    abstract val version: Property<String>
//
//    @get:org.gradle.api.tasks.OutputDirectory
//    abstract val outputDir: DirectoryProperty
//}
//
//val pluginVersionTaskProvider = tasks.register("pluginVersion", GeneratePluginVersion::class.java) {
//    outputDir.set(project.layout.buildDirectory.dir("generated/sources/com/solanamobile/buoy"))
//    version.set(project.version.toString())
//}
//
//configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
//    val versionFileProvider = pluginVersionTaskProvider.flatMap { it.outputDir }
//    sourceSets.getByName("main").kotlin.srcDir(versionFileProvider)
//}

dependencies {
    implementation(kotlin("stdlib"))

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