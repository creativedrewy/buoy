package com.solanamobile.buoy

import com.android.build.gradle.AppExtension
import com.solanamobile.buoy.task.ProcessIdlTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

//buoyGenerator {
//    idlFilePath = file('src/test.json')
//}

class GeneratorPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        val taskProvider = project.tasks.register<ProcessIdlTask>("buoyGenerator")
        project.tasks.findByPath("assemble")?.dependsOn("buoyGenerator")

        val saveDir = File(project.buildDir.path + "/generated/source/buoy")
        saveDir.mkdirs()

        project.tasks
            .withType(KotlinCompile::class.java)
            .configureEach {
                source(saveDir)
            }

        project.afterEvaluate {
            val androidExtension = project.extensions.findByType(AppExtension::class.java)

            androidExtension?.applicationVariants?.forEach { variant ->
                variant.registerJavaGeneratingTask(taskProvider, saveDir)
                variant.addJavaSourceFoldersToModel(saveDir)
            }
        }
    }
}