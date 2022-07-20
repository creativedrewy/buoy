package com.solanamobile.buoy.playground.idlspec

data class IdlRootV1(
    val version: String,
    val name: String,
    val instructions: List<Instruction>,
    val accounts: List<AccountData>,
    val errors: List<Error>
)

data class Instruction(
    val name: String,
    val accounts: List<AccountMetaDescriptor>,
    val args: List<Arg>
)

data class AccountMetaDescriptor(
    val name: String,
    val isMut: Boolean,
    val isSigner: Boolean
)

data class Arg(
    val name: String,
    val type: String
)

data class AccountData(
    val name: String,
    val type: AccountDataType
)

data class AccountDataType(
    val kind: String,
    val fields: List<DataField>
)

//TODO: handle deserialize for type: { "option":"i64" }
data class DataField(
    val name: String,
    val type: String
)

data class Error(
    val code: Number,
    val name: String,
    val msg: String
)