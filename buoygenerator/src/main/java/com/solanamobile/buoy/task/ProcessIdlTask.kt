package com.solanamobile.buoy.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class ProcessIdlTask @Inject constructor(objects: ObjectFactory) : DefaultTask() {

    @InputFile
    @Optional
    val idlFilePath: RegularFileProperty = objects.fileProperty()

    @TaskAction
    fun processIdl() {
        println(":: You have provided path: ${ idlFilePath.orNull?.asFile.toString() } ::")
    }

}