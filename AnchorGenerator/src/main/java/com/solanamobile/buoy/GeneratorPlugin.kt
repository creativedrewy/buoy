package com.solanamobile.buoy

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

class GeneratorPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register<MyTask>("something") {
            dependsOn("assemble")
        }
    }
}

class MyTask: DefaultTask() {

    @TaskAction
    fun doSomething() {
        println(":: You have made a plugin!! ::")
    }

}