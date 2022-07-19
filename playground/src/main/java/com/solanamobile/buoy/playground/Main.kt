package com.solanamobile.buoy.playground

import com.squareup.kotlinpoet.*

/**
 * ./gradlew :playground:run --args=<Path_to_json>
 */
fun main(arguments: Array<String>) {
    println("----------------------------------------")
    println("")

    val greeterClass = ClassName("", "Greeter")
    val file = FileSpec.builder("com.solanamobile", "HelloWorld")
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
        .build()

    file.writeTo(System.out)
}