package com.solanamobile.buoy.playground

import com.solanamobile.web3.core.AccountMeta
import com.solanamobile.web3.core.PublicKey
import com.solanamobile.web3.core.TransactionInstruction

class ContractReference {

    fun placeBid(
        bidAmount: ULong,
        bump: UByte,
        walvalBump: UByte,
        expiryDate: ULong?,
        bidder: PublicKey,
        pdaBidderDataAccount: PublicKey,
        pdaBidderDepositAccount: PublicKey,
        escrowAccount: PublicKey,
        systemProgram: PublicKey,
        programId: PublicKey
    ): TransactionInstruction {

        val keys = listOf(
            AccountMeta(publicKey = bidder, isSigner = true, isWritable = true),
            AccountMeta(publicKey = pdaBidderDataAccount, isSigner = false, isWritable = true),
            AccountMeta(publicKey = pdaBidderDepositAccount, isSigner = false, isWritable = true),
            AccountMeta(publicKey = escrowAccount, isSigner = false, isWritable = false),
            AccountMeta(publicKey = systemProgram, isSigner = false, isWritable = false)
        )

        val sigHash: Byte = 0x1 //TODO: impl AnchorKit.sighash(nameSpace: "global", name: "place_bid")

//        val bidAmountData = bidAmount.serialize()
//        val bidAmountBytes = (bidAmountData as BytesEncodable).bytes
//        val bumpData = bump.serialize()
//        val bumpBytes = (bumpData as BytesEncodable).bytes
//        val walvalBumpData = walvalBump.serialize()
//        val walvalBumpBytes = (walvalBumpData as BytesEncodable).bytes
//        val expiryDateData = expiryDate.serialize()
//        val expiryDateBytes = BytesEncodable).bytes

//        val instructionData: ByteArray = sigHash + bidAmountBytes + bumpBytes + walvalBumpBytes + expiryDateBytes

        return TransactionInstruction(
            keys = keys,
            programId = programId,
            data = byteArrayOf()
        )
    }

}

/**
 * TOD: Figure out how this is going to be decode-able
 */
class BidAccount(
    val anchorAccountBuffer: Long,
    val bidderKey: PublicKey,
    val bidAmount: Long,
    val escrowKey: PublicKey,
    val bump: UByte,
    val walletBump: UByte,
    val initializerKey: PublicKey,
    val initializerDepositTokenAccount: PublicKey,
    val expiryDate: Long?
) {
    companion object {
        val optionalPropertyNames = listOf("expiryDate")
    }
}

class EscrowAccount(
    val anchorAccountBuffer: Long,
    val initializerKey: PublicKey,
    val initializerDepositTokenAccount: PublicKey,
    val takerAmount: Long
) {
    companion object {
        val optionalPropertyNames = listOf<String>()
    }
}