package com.solanamobile.buoy

import com.solanamobile.buoy.task.ProcessIdlTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class GeneratorPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register<ProcessIdlTask>("idlGenerator")
        project.tasks.findByPath("assemble")?.dependsOn("idlGenerator")

        //TODO: Ensure dependencies for the generated code
        //TODO: Add generated output to source sets
    }
}