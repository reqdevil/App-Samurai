package com.example.assignment_kotlin.service

import android.content.Context
import android.graphics.Bitmap
import android.media.metrics.Event
import android.util.Log
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.*
import com.example.assignment_kotlin.utilities.imageList


class DataService(context: Context) {
    private val TAG = "Data Service"

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

    fun getImage(tag: String, completion: (Bitmap?, VolleyError?) -> Unit) {
        val image = rand(20, imageList.size - 1) // Highest Resolution
//        val image = rand(0, imageList.size -1) // Any resolution

        val request = ImageRequest(
            imageList[image],
            { bitmap ->
                completion(bitmap, null)
            },
            0, 0,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.ARGB_8888,
            { error ->
                completion(null, error)
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