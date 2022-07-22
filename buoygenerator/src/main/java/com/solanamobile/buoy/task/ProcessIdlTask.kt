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

        val saveDir = File(project.buildDir.path + "/generated/source/buoy")
        saveDir.mkdirs()

        val file = FileSpec.builder("com.solanamobile.buoy", "Greeter")
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