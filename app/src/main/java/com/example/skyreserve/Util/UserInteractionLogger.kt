package com.example.skyreserve.Util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*

import android.util.Log // Import the Log class

class UserInteractionLogger(private val context: Context, private val username: String) {

    private val logFile = File(context.filesDir, "$username-log.txt")
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    private val TAG = "UserInteractionLogger" // TAG used for logging

    // Call this whenever an interaction occurs
    fun logInteraction(interaction: String) {
        val timestamp = dateFormat.format(Date())
        val logMessage = "$timestamp: $interaction\n"

        try {
            FileOutputStream(logFile, true).use { fos ->
                PrintWriter(fos).apply {
                    append(logMessage)
                    flush()
                    close()
                }
            }
            Log.d(TAG, "context: ${context}")
            Log.d(TAG, "Logged interaction: $logMessage")
        } catch (e: Exception) {
            Log.e(TAG, "Error logging interaction", e)
        }
    }

    // Call this when the user completes the task to send the log
    fun sendLogToEmail(activityContext: Context) {
        try {
            val recipientEmail = "cfiguer055@gmail.com"
            val logFileUri: Uri = FileProvider.getUriForFile(
                activityContext.applicationContext,
                "${activityContext.packageName}.provider",
                logFile
            )
            Log.d(TAG, "File URI obtained: $logFileUri")
            Log.d(TAG, "context: ${activityContext}")
            Log.d(TAG, "context.applicationContext: ${activityContext.applicationContext}")
            Log.d(TAG, "context.packageName.provider: ${activityContext.packageName}.provider")
            Log.d(TAG, "logFile: $logFile")


            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
                putExtra(Intent.EXTRA_SUBJECT, "User Interaction Log")
                putExtra(Intent.EXTRA_TEXT, "Please find the attached log file.")
                putExtra(Intent.EXTRA_STREAM, logFileUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            Log.d(TAG, "Email intent: $emailIntent")
            Log.d(TAG, "Email intent set up and ready to send.")

            // Grant permission explicitly to the email app (replace "com.email.app" with actual package name of the email app)
            // activityContext.grantUriPermission("com.email.app", logFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            activityContext.startActivity(Intent.createChooser(emailIntent, "Choose an email client:"))
            Log.d(TAG, "Email chooser intent started.")
        } catch (e: Exception) {
            Log.e(TAG, "Error sending email", e)
        }
    }
}
