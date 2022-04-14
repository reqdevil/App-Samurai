package com.example.assignment_kotlin.controller

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment_kotlin.R
import com.example.assignment_kotlin.service.ToastService
import com.example.assignment_kotlin.utilities.ToastType


class StoryActivity: AppCompatActivity() {
    private val TAG = "Story Activity"

    companion object {
        var bitmap: Bitmap? = null
    }

    private lateinit var mainHandler: Handler
    private var paused = false

    private lateinit var imageView: ImageView
    private lateinit var profilePicture: ImageView
    private lateinit var profileName: TextView
    private lateinit var progressBar: ProgressBar

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        var profilePictureImage = intent.getIntExtra("profilePicture", 0)
        var profileNameText = intent.getStringExtra("profileName")
        var beginTime = intent.getLongExtra("beginTime", 0)
        var closeButton: ImageView

        progressBar = findViewById(R.id.progressBar)
        profilePicture = findViewById(R.id.profilePicture)
        profileName = findViewById(R.id.profileName)
        imageView = findViewById(R.id.imageView)
        closeButton = findViewById(R.id.closeButton)
        closeButton.setOnClickListener() { finish() }

        mainHandler = Handler(Looper.getMainLooper())

        imageView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    paused = true
                }

                MotionEvent.ACTION_UP -> {
                    paused = false
                }
            }

            true
        }

        loadUser(profilePictureImage, profileNameText)
        loadImage(beginTime)
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateStoryTime)
    }

    override fun onDestroy() {
        super.onDestroy()
        bitmap = null
    }

    private fun loadUser(image: Int, name: String?) {
        if (image != 0 || name != null) {
            profilePicture.setImageResource(image)
            profileName.text = name
        } else {
            ToastService.instance.showToast(
                window,
                applicationContext,
                ToastType.ERROR,
                "Failed to load user information."
            )
        }
    }

    private fun loadImage(beginTime: Long) {
        imageView.setImageBitmap(bitmap)
        val processTime = System.currentTimeMillis() - beginTime

        Log.e(TAG, "Data loaded in $processTime ms")
        ToastService.instance.showToast(window, applicationContext, ToastType.INFORMATION, "Data loaded in $processTime ms")

        mainHandler.post(updateStoryTime)
    }

    private val updateStoryTime = object : Runnable {
        override fun run() {
            if (!paused) {
                if (progressBar.progress < 100) {
                    progressBar.progress += 2
                }

                if (progressBar.progress >= 100) {
                    imageView.setImageResource(0)
                    finish()
                }

            }

            mainHandler.postDelayed(this, 100)
        }
    }
}
