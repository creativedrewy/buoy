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

        val saveDir = File(project.buildDir.path + "/generated/source/buoy")
        saveDir.mkdirs()

        project.afterEvaluate {
            val container = project.container(BaseVariant::class.java)
            val androidExtension = project.extensions.findByType(AppExtension::class.java)

            androidExtension?.applicationVariants?.forEach { variant ->
                container.add(variant)

                variant.registerJavaGeneratingTask(taskProvider, saveDir)
                variant.addJavaSourceFoldersToModel(saveDir)
            }

//            override val sourceSets: DomainObjectCollection<Named>
//            get() = with(project.extensions.getByName("kotlin")) {
//                @Suppress("UNCHECKED_CAST")
//                javaClass.getMethod("getSourceSets")
//                    .invoke(this) as DomainObjectCollection<Named>
//            }

//            override fun onSourceSetAdded(sourceSet: Named, spec: BuildConfigClassSpec) {
//                (sourceSet.javaClass.getMethod("getKotlin")
//                    .invoke(sourceSet) as SourceDirectorySet)
//                    .srcDir(spec.generateTask)
//            }

            container.all {
                registerJavaGeneratingTask(taskProvider, saveDir)
                addJavaSourceFoldersToModel(saveDir)
            }
        }
    }
}

//fun createAllKotlinSourceSetServices(
//    apolloExtension: DefaultApolloExtension,
//    project: Project,
//    sourceFolder: String,
//    nameSuffix: String,
//    action: Action<Service>,
//) {
//  //extensions.findByName("kotlin") as? KotlinProjectExtension
//
//  project.kotlinProjectExtensionOrThrow.sourceSets.forEach { kotlinSourceSet ->
//    val name = "${kotlinSourceSet.name}${nameSuffix.capitalizeFirstLetter()}"
//
//    apolloExtension.service(name) { service ->
//      action.execute(service)
//      check(!service.sourceFolder.isPresent) {
//        "Apollo: service.sourceFolder is not used when calling createAllKotlinJvmSourceSetServices. Use the parameter instead"
//      }
//      service.srcDir("src/${kotlinSourceSet.name}/graphql/$sourceFolder")
//      (service as DefaultService).outputDirAction = Action<Service.DirectoryConnection> { connection ->
//        kotlinSourceSet.kotlin.srcDir(connection.outputDir)
//      }
//    }
//  }
//}

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