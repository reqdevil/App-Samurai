package com.example.assignment_kotlin.service

import android.content.Context
import android.graphics.Bitmap
import android.media.metrics.Event
import android.util.Log
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.example.assignment_kotlin.utilities.imageList


class DataService(context: Context) {
    private val TAG = "Data Service"
    private val baseURL: String = "https://httpbin.org/"

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    companion object {
        @Volatile
        private var INSTANCE: DataService? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataService(context).also {
                    INSTANCE = it
                }
            }
    }

    fun downloadImage(tag: String, completion: (List<Bitmap>) -> Unit) {
        val bitmapList: MutableList<Bitmap> = arrayListOf()
        val imageCount = 5
        var finishedImage = 0

        val imageThread = Thread(
            Runnable {
                for (i in 1..imageCount) {
                    val image = rand(0, 23)

                    val request = ImageRequest(
                        imageList[image],
                        { bitmap ->
                            bitmapList.add(bitmap)
                        },
                        0, 0,
                        ImageView.ScaleType.CENTER_CROP,
                        Bitmap.Config.ARGB_8888,
                        { error ->
                            // TODO: SHOW TOAST IN TOAST SERVICE
                        }
                    )
                    request.tag = tag + "$i"

                    addToRequestQueue(request)
                }
            }
        )

        imageThread.start()

        requestQueue.addRequestEventListener { request, event ->
            if (event == 5) {
                finishedImage++
            }

            if (finishedImage == imageCount) {
                completion(bitmapList)
            }
        }
    }

    private fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    private fun rand(start: Int, end: Int): Int {
        return (start..end).random()
    }
}