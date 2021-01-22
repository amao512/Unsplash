package com.aslnstbk.unsplash.image_details.data

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.widget.Toast
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import kotlin.random.Random

class ImageDownload {

    private var downloadId: Long = 0

    fun download(
        url: String,
        description: String
    ) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(generateTitle())
            .setDescription(description)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverMetered(true)

        val dm: DownloadManager = APP_ACTIVITY.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadId = dm.enqueue(request)

        registerReceiver()
    }

    private fun generateTitle(): String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return (1..8)
            .map { _ -> Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    private fun registerReceiver(){
        val br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id: Long? = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == downloadId){
                    Toast.makeText(APP_ACTIVITY.applicationContext, "Downlaod completed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        APP_ACTIVITY.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }
}