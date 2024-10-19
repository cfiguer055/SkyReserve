package com.example.skyreserve.util

import android.util.Base64
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {

//    private var key : String = "mysecretkey12345"
//    private var secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/ECB/PKCS5Padding"

    private val key: Key = SecretKeySpec("mysecretkey12345".toByteArray(), ALGORITHM) // Use a secure key

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key)
//        val encryptedBytes = cipher.doFinal(input.toByteArray())
//        val encryptedBytes = cipher.doFinal(input.toByteArray(charset("UTF-8")))
        val encryptedBytes = cipher.doFinal(input.toByteArray(Charsets.UTF_8))

        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    fun decrypt(encrypted: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key)
//        val decryptedBytes = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT))
        val decryptedBytes = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT))

        //return String(decryptedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}
