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
        create("simplePlugin") {
            id = "buoy-plugin"
            implementationClass = "com.solanamobile.buoy.GeneratorPlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.android.tools.build:gradle:4.0.0")
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

//publishing {
//    publications {
//        mavenJava(MavenPublication) {
//            artifact("$buildDir/outputs/aar/solana-release.aar")  {
//                builtBy tasks.getByName("assemble")
//            }
//            artifactId 'solana'
//            versionMapping {
//                usage('java-api') {
//                    fromResolutionOf('runtimeClasspath')
//                }
//                usage('java-runtime') {
//                    fromResolutionResult()
//                }
//            }
//
//        }
//    }
//}