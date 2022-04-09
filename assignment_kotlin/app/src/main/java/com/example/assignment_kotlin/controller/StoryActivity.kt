package com.example.assignment_kotlin.controller

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.assignment_kotlin.R

class StoryActivity: AppCompatActivity() {
    private val TAG = "Story Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        val imageCount = intent.getIntExtra("imageCount", 0)

        Log.e(TAG, imageCount.toString())
    }
}
