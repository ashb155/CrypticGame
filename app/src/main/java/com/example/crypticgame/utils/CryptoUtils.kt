package com.example.crypticgame.utils

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object CryptoUtils {
    fun decryptPayload(userInput: String, encryptedPayloadBase64: String): String? {
        return try {
            val keyBytes = MessageDigest.getInstance("SHA-256").digest(userInput.toByteArray())
            val secretKey = SecretKeySpec(keyBytes, "AES")

            val parts = encryptedPayloadBase64.split(":")
            if (parts.size != 2) return null

            val iv = Base64.decode(parts[0], Base64.DEFAULT)
            val cipherText = Base64.decode(parts[1], Base64.DEFAULT)

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))

            String(cipher.doFinal(cipherText))
        }
        catch (e: Exception) {
            null
        }
    }
}