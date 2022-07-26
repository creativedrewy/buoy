package com.solanamobile.buoy.playground

import com.google.gson.Gson
import com.portto.solana.web3.PublicKey
import com.portto.solana.web3.TransactionInstruction
import com.solanamobile.buoy.playground.idlspec.IdlRootV1
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import java.security.MessageDigest
import kotlin.reflect.KClass

/**
 * ./gradlew :playground:run --args=<absolute_path_to_json>
 */

fun classTypeForArgType(argType: String): KClass<*> {
    return when (argType) {
        "u8" -> UByte::class
        "u16" -> UShort::class
        "u32" -> UInt::class
        "u64" -> ULong::class
        "i8" -> Byte::class
        "i16" -> Short::class
        "i32" -> Int::class
        "i64" -> Long::class
        "publicKey" -> PublicKey::class
        else -> {
            return ClassName("solanapackagename", argType)::class
        }
    }
}

private fun convertCamelToSnakeCase(camelCase: String): String {
    val snakeCase = StringBuilder()

    for (character in camelCase) {
        if (character.isUpperCase()) {
            snakeCase.append("_${character.toLowerCase()}")
        } else {
            snakeCase.append(character)
        }
    }

    return snakeCase.removePrefix("_").toString()
}

fun sighash(nameSpace: String = "global", name: String): Array<Byte> {
    val preImage = String.format("%s:%s", nameSpace, name)
    val preImageByteArray = preImage.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(preImageByteArray)
    val sighash = digest.copyOfRange(0, 8)
    return sighash.toTypedArray()
}

fun main(arguments: Array<String>) {
    println("----------------------------------------")
    println(":: Beginning code generation ::")
    println("")

    val gson = Gson()

    //Parse the IDL Json and deserialize into objects
    val jsonSrc = File(arguments[0]).readText()
    val idlSource = gson.fromJson(jsonSrc, IdlRootV1::class.java)

    //Use the objects to generate the contract file
    val contractFile = FileSpec.builder("com.solanamobile.buoy", idlSource.name)

    val mainClass = contractFile.addType(
        TypeSpec.classBuilder(idlSource.name)
            .build()
    )

    idlSource.instructions.forEach { instruction ->
        val functionBuilder = FunSpec.builder(instruction.name)

        functionBuilder.returns(TransactionInstruction::class)

        // start the list of function
        functionBuilder.addStatement("val = listOf(")

        // for each meta descriptor, add a parameter accepting
        // the pubkey and generate the AccountMeta item in the list
        instruction.args.forEach { arg ->
            val type = classTypeForArgType(arg.type)
            functionBuilder.addParameter(arg.name, type)
        }

        instruction.accounts.forEach { metaDescriptor ->
            val metaItem = String.format(
                "\tAccountMeta(publicKey = %s, isSigner = %s, isWritable = %s)",
                metaDescriptor.name,
                metaDescriptor.isSigner.toString(),
                metaDescriptor.isMut.toString()
            )

            functionBuilder
                .addParameter(metaDescriptor.name, PublicKey::class)
                .addStatement(metaItem)
        }

        // close the listOf function
        functionBuilder.addStatement(")")

        val sighHashStatement = String.format(
            "val sigHash: Array<Byte> = sighash(\"%s\", \"%s\")",
            "global",
            convertCamelToSnakeCase(instruction.name)
        )
        functionBuilder.addStatement(sighHashStatement)

        val instructionDataStatementBuilder = StringBuilder()
        instructionDataStatementBuilder.append("val instructionData: Array<Byte> = sigHash")

        instruction.args.forEach { arg ->
            functionBuilder.addStatement(String.format("val %sBytes = %s.toBytes()", arg.name, arg.name))
            instructionDataStatementBuilder.append(String.format(" + %sBytes", arg.name, arg.name))
        }

        val instructionDataStatement = instructionDataStatementBuilder.toString()

        functionBuilder.addStatement(instructionDataStatement)

        functionBuilder.addStatement("return TransactionInstruction(")
        functionBuilder.addStatement("\tkeys = keys,")
        functionBuilder.addStatement("\tprogramId = programId,")
        functionBuilder.addStatement("\tdata = instructionData")
        functionBuilder.addStatement(")")

        mainClass.addFunction(
            functionBuilder.build()
        ).build()
    }

    contractFile.build().writeTo(System.out)
}