package com.solanamobile.buoy.anchorgenerator

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class GeneratorPlugin: Plugin<Project> {
    override fun apply(project: Project) {

    }
}

class MyTask: DefaultTask() {

    @TaskAction
    fun doSomething() {
        println(":: You have made a plugin!! ::")
    }

}