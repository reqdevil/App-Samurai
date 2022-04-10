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

    private val requestQueue: RequestQueue by lazy {
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

    fun getImage(tag: String, completion: (Bitmap, Long) -> Unit) {
        val beginTime = System.currentTimeMillis()
        val image = rand(0, 23)

        val request = ImageRequest(
            imageList[image],
            { bitmap ->
                completion(bitmap, beginTime)
            },
            0, 0,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.ARGB_8888,
            { error ->
                // TODO: SHOW TOAST IN TOAST SERVICE
            }
        )
        request.tag = tag

        addToRequestQueue(request)
    }

    private fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    private fun rand(start: Int, end: Int): Int {
        return (start..end).random()
    }
}