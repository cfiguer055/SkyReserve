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

class UserInteractionLogger(private val context: Context) {
    private lateinit var username: String
    private lateinit var logFile: File
    private val allUsernames = mutableListOf<String>()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    private val TAG = "UserInteractionLogger"
    private val recipientEmail = "cfiguer055@gmail.com"

    fun init(username: String) {
        this.username = username
        this.logFile = File(context.filesDir, "$username-log.txt")
        if (!allUsernames.contains(username)) {
            allUsernames.add(username)
        }
    }

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

    fun sendAllLogsToEmail(activityContext: Context) {
        val combinedLogFileName = "combined-user-logs.txt"
        val combinedLogFile = File(context.filesDir, combinedLogFileName)
        combinedLogFile.bufferedWriter().use { writer ->
            allUsernames.forEach { uname ->
                val individualLogFile = File(context.filesDir, "$uname-log.txt")
                if (individualLogFile.exists()) {
                    writer.write("Logs for user: $uname\n")
                    individualLogFile.forEachLine { line ->
                        writer.write(line)
                        writer.newLine()
                    }
                    writer.newLine() // Add an extra newline for separation between user logs
                }
            }
        }

        val combinedLogFileUri: Uri = FileProvider.getUriForFile(
            activityContext.applicationContext,
            "${activityContext.packageName}.provider",
            combinedLogFile
        )

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
            putExtra(Intent.EXTRA_SUBJECT, "Combined User Interaction Logs")
            putExtra(Intent.EXTRA_TEXT, "Please find the attached combined log file.")
            putExtra(Intent.EXTRA_STREAM, combinedLogFileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        activityContext.startActivity(Intent.createChooser(emailIntent, "Choose an email client:"))
    }

    fun sendUsernamesListToEmail(activityContext: Context) {
        val usernamesListFile = File.createTempFile("usernames-list", ".txt", context.cacheDir)
        FileOutputStream(usernamesListFile, true).bufferedWriter().use { writer ->
            allUsernames.forEach { uname ->
                writer.write(uname)
                writer.newLine()
            }
        }

        val usernamesListFileUri: Uri = FileProvider.getUriForFile(
            activityContext.applicationContext,
            "${activityContext.packageName}.provider",
            usernamesListFile
        )

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
            putExtra(Intent.EXTRA_SUBJECT, "List of Usernames")
            putExtra(Intent.EXTRA_TEXT, "Please find the attached list of usernames.")
            putExtra(Intent.EXTRA_STREAM, usernamesListFileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        activityContext.startActivity(Intent.createChooser(emailIntent, "Choose an email client:"))
    }
}
