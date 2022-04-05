package com.example.assignment_kotlin.service

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

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

    fun downloadJPEG(tag: String, path: String): StringRequest {
        val strReq = StringRequest(
            Request.Method.GET, baseURL + path,
            { response ->
                Log.e(TAG, response.substring(0, 500))
            },
            { error -> Log.e(TAG, error.localizedMessage ) }
        )
        strReq.tag = TAG

        return strReq
    }

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,
            object : ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap {
                    return cache.get(url)
                }
                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }
            })
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}