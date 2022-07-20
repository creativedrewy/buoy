package com.solanamobile.buoy.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class ProcessIdlTask : DefaultTask() {

    @TaskAction
    fun processIdl() {
        println(":: You will process the IDL here! ::")
    }

}