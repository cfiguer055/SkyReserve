package com.example.skyreserve.Util

import android.util.Base64
import java.security.Key
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private const val KEY_LENGTH_BYTES = 16

    // Generate a random IV
    private val iv = ByteArray(KEY_LENGTH_BYTES).apply { SecureRandom().nextBytes(this) }

    // Ensure the key length is 256 bits (32 bytes)
    private val key: Key = SecretKeySpec("Your32CharacterLongKeyForTheAES!".toByteArray(Charsets.UTF_8), ALGORITHM)

    fun encrypt(input: String): String {

        try {
            val cipher = Cipher.getInstance("AES/GCM/NoPadding", "GmsCore_OpenSSL")
            // Use the cipher for encryption/decryption
        } catch (e: NoSuchProviderException) {
            // Fallback to a default provider if GmsCore_OpenSSL is not available
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            // Use the cipher for encryption/decryption
        } catch (e: NoSuchAlgorithmException) {
            // Handle the case where the algorithm is not available
        } catch (e: NoSuchPaddingException) {
            // Handle the case where the padding is not available
        }


        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(iv))
        val encryptedBytes = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    fun decrypt(encrypted: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        val decryptedBytes = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT))
        return String(decryptedBytes, Charsets.UTF_8)
    }
}

//package com.example.skyreserve.Util
//
//import android.util.Base64
//import java.security.Key
//import javax.crypto.Cipher
//import javax.crypto.spec.SecretKeySpec
//
//object EncryptionUtil {
//
//    private const val ALGORITHM = "AES"
//    private const val TRANSFORMATION = "AES"
//
//    //private val key: Key = SecretKeySpec("YourSecretKey1234".toByteArray(), ALGORITHM) // Use a secure key
//
//    // Ensure the key length is 256 bits (32 characters)
//    private val key: Key = SecretKeySpec("YourVerySecretKeyForAES256BitEncryption!".toByteArray(), ALGORITHM)
//
//
//    fun encrypt(input: String): String {
//        val cipher = Cipher.getInstance(TRANSFORMATION)
//        cipher.init(Cipher.ENCRYPT_MODE, key)
//        val encryptedBytes = cipher.doFinal(input.toByteArray())
//        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
//    }
//
//    fun decrypt(encrypted: String): String {
//        val cipher = Cipher.getInstance(TRANSFORMATION)
//        cipher.init(Cipher.DECRYPT_MODE, key)
//        val decryptedBytes = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT))
//        return String(decryptedBytes)
//    }
//}
