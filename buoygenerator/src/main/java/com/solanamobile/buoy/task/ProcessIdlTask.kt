package com.solanamobile.buoy.task

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

abstract class ProcessIdlTask @Inject constructor(objects: ObjectFactory) : DefaultTask() {

    @InputFile
    @Optional
    val idlFilePath: RegularFileProperty = objects.fileProperty()

    @TaskAction
    fun processIdl() {
        //TODO: Ensure dependencies for the generated code
        println(":: You have provided path: ${ idlFilePath.orNull?.asFile.toString() } ::")

        val saveDir = File(project.buildDir.path + "/generated/sources/com/solanamobile/buoy")
        saveDir.mkdirs()

//        val ext = project.extensions.getByType(AppExtension::class.java)
//        println(":: Your Android ext: ${ ext.toString() } ::")
//
//        val container = project.container(BaseVariantImpl::class.java)
//        val androidExtension = project.extensions.findByType(BaseExtension::class.java)
//
//        val androidSourceSets = androidExtension?.sourceSets?.getByName("main")
        //androidSourceSets?.kotlinSourceSet

//        container.forEach { baseVariant ->
//            if (baseVariant.sourceSets.any { it.name == "main" }) {
//                try {
//
//                } catch (e: Exception) {
//
//                }
//            }
//        }

        //com.android.build.gradle.AppExtension
//    container.all {
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


//        val container = project.extensions.findByType(SourceSetContainer::class.java)
//        container?.forEach { srcSet ->
//            srcSet.allSource.srcDir(saveDir.path)
//        }

        val file = FileSpec.builder("", "Greeter")
            .addType(
                TypeSpec.classBuilder("Greeter")
                    .primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameter("name", String::class)
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("name", String::class)
                            .initializer("name")
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("greet")
                            .addStatement("println(%P)", "Hello, \$name")
                            .build()
                    )
                    .build()
            )
//            .addFunction(
//                FunSpec.builder("main")
//                    .addParameter("args", String::class, KModifier.VARARG)
//                    .addStatement("%T(args[0]).greet()", greeterClass)
//                    .build()
//            )
            .build()

        file.writeTo(saveDir)
    }

}