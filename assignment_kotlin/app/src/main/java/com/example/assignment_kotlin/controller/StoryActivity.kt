package com.example.assignment_kotlin.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.assignment_kotlin.R
import com.example.assignment_kotlin.service.DataService
import com.example.assignment_kotlin.utilities.personList

class StoryActivity: AppCompatActivity() {
    private val TAG = "Story Activity"

    private lateinit var mainHandler: Handler
    private var position: Int = 0

    private lateinit var imageView: ImageView
    private lateinit var profilePicture: ImageView
    private lateinit var profileName: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        position = intent.getIntExtra("position", position)
        progressBar = findViewById(R.id.progressBar)
        profilePicture = findViewById(R.id.profilePicture)
        profileName = findViewById(R.id.profileName)
        imageView = findViewById(R.id.imageView)

        mainHandler = Handler(Looper.getMainLooper())

        loadUser()
        loadImage()
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

    private fun loadUser() {
        val user = personList[position]

        profilePicture.setImageResource(user.image)
        profileName.text = user.name
    }

    private fun loadImage() {
        DataService.getInstance(applicationContext).getImage(TAG, completion = { bitmap, beginTime ->
            imageView.setImageBitmap(bitmap)

            val processTime = System.currentTimeMillis() - beginTime
            Log.e(TAG, "$processTime ms")

            mainHandler.post(updateTextTask)
        })
    }

    private val updateTextTask = object : Runnable {
        override fun run() {
            if (progressBar.progress < 100) {
                progressBar.progress += 2
            }

            if (progressBar.progress >= 100) {
                imageView.setImageResource(0)
                finish()
            }

            mainHandler.postDelayed(this, 100)
        }
    }
}
