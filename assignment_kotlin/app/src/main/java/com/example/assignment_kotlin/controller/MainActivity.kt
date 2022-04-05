package com.example.assignment_kotlin.controller

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.assignment_kotlin.R
import com.example.assignment_kotlin.model.Story
import com.example.assignment_kotlin.service.DataService
import com.example.assignment_kotlin.utilities.personList
import com.example.assignment_kotlin.view.StoryAdapter

class MainActivity : AppCompatActivity() {
    private val TAG: String = "Main Activity"
    private lateinit var queue: RequestQueue

    private lateinit var storyView: RecyclerView
    private lateinit var imageView: ImageView
    private lateinit var downloadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)

        storyView = findViewById(R.id.storyView)
        imageView = findViewById(R.id.imageView)
        downloadButton = findViewById(R.id.downloadButton)
        downloadButton.setOnClickListener() { downloadButtonPressed() }

        loadStoryRecyclerView()
    }

    private fun downloadButtonPressed() {
        val queue = DataService.getInstance(this.applicationContext).requestQueue
        val request = DataService.getInstance(this.applicationContext).downloadJPEG(TAG, "image/jpeg")
        DataService.getInstance(this).addToRequestQueue(request)
    }

    private fun loadStoryRecyclerView() {
        storyView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val data = ArrayList<Story>()
        for (person in personList) {
            data.add(Story(person.image, person.name))
        }

        val adapter = StoryAdapter(data)
        storyView.adapter = adapter
    }
}