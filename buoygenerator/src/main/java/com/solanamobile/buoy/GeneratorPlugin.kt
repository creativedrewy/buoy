package com.solanamobile.buoy

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.BaseVariant
import com.solanamobile.buoy.task.ProcessIdlTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import java.io.File

class GeneratorPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        val taskProvider = project.tasks.register<ProcessIdlTask>("buoyGenerator")
        project.tasks.findByPath("assemble")?.dependsOn("buoyGenerator")

        project.afterEvaluate {
            val saveDir = File(project.buildDir.path + "/generated/sources/com/solanamobile/buoy")
            saveDir.mkdirs()

            val container = project.container(BaseVariant::class.java)
            val androidExtension = project.extensions.findByType(AppExtension::class.java)

            androidExtension?.applicationVariants?.forEach { variant ->
                container.add(variant)
            }

            container.all {
//                javaClass.getMethod("registerJavaGeneratingTask", TaskProvider::class.java, Array<File>::class.java)
//                    .invoke(this, taskProvider, saveDir)

                registerJavaGeneratingTask(taskProvider, saveDir)
                addJavaSourceFoldersToModel(saveDir)
            }
        }
    }
}

//container.all {
//    if (it.sourceSets.any { it.name == sourceSetName }) {
//      if (kotlinSourceSet == null) {
//        try {
//          // AGP 7.0.0+: do things lazily
//          it.javaClass.getMethod("registerJavaGeneratingTask", TaskProvider::class.java, Array<File>::class.java)
//              .invoke(it, taskProvider, arrayOf(outputDir.get().asFile))
//        } catch (e: Exception) {
//          // Older AGP: do things eagerly
//          it.registerJavaGeneratingTask(taskProvider.get(), outputDir.get().asFile)
//        }
//      } else {
//        // The kotlinSourceSet carries task dependencies, calling srcDir() above is enough
//        // to setup task dependencies
//        // addJavaSourceFoldersToModel is still required for AS to see the sources
//        // See https://github.com/apollographql/apollo-android/issues/3351
//        it.addJavaSourceFoldersToModel(outputDir.get().asFile)
//      }
//    }