package com.example.skyreserve.Util

import android.util.Base64
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES"

    //private val key: Key = SecretKeySpec("YourSecretKey1234".toByteArray(), ALGORITHM) // Use a secure key

    // Ensure the key length is 256 bits (32 characters)
    private val key: Key = SecretKeySpec("YourVerySecretKeyForAES256BitEncryption!".toByteArray(), ALGORITHM)


    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedBytes = cipher.doFinal(input.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    fun decrypt(encrypted: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decryptedBytes = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT))
        return String(decryptedBytes)
    }
}
