package com.priambudi19.carifoto.download

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.priambudi19.carifoto.R
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class FotoDownloadManager(private val context: Context, param: WorkerParameters) :
    CoroutineWorker(context, param) {
    private val fileUrl = inputData.getString(FileParams.KEY_FILE_URL) ?: ""
    private val fileName = inputData.getString(FileParams.KEY_FILE_NAME) ?: ""
    private val fileType = inputData.getString(FileParams.KEY_FILE_TYPE) ?: ""

    override suspend fun doWork(): Result {
        val builder = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Downloading your file...")
            .setOngoing(true)
            .setProgress(0, 0, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = NotificationConstants.CHANNEL_NAME
            val description = NotificationConstants.CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NotificationConstants.CHANNEL_ID, name, importance)
            channel.description = description

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(NotificationConstants.NOTIFICATION_ID,builder.build())
        }



        NotificationManagerCompat.from(context)
            .notify(NotificationConstants.NOTIFICATION_ID, builder.build())
        val uri = download(fileUrl)

        NotificationManagerCompat.from(context).cancel(NotificationConstants.NOTIFICATION_ID)


        return if (uri != null) {
            Result.success(workDataOf(FileParams.KEY_FILE_URI to uri.toString()))
        } else {
            Result.failure()
        }

    }


    private fun download(url: String?): Uri? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, fileType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/CariFoto")
            }
            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            return if (uri != null) {
                URL(url).openStream().use { input ->
                    resolver.openOutputStream(uri).use { output ->
                        input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                    }
                }
                uri
            } else {
                null
            }
        } else {
            val target = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )
            URL(url).openStream().use { input ->
                FileOutputStream(target).use { output ->
                    input.copyTo(output)
                }
            }

            return target.toUri()
        }

    }



    object FileParams {
        const val KEY_FILE_URL = "key_file_url"
        const val KEY_FILE_TYPE = "key_file_type"
        const val KEY_FILE_NAME = "key_file_name"
        const val KEY_FILE_URI = "key_file_uri"
    }

    object NotificationConstants {
        const val CHANNEL_NAME = "download_file_worker_channel"
        const val CHANNEL_DESCRIPTION = "download_file_worker"
        const val CHANNEL_ID = "download_file_worker_channel_1"
        const val NOTIFICATION_ID = 1
    }
}


