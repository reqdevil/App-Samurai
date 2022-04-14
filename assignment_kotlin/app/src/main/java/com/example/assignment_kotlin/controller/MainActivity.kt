package com.example.assignment_kotlin.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.assignment_kotlin.R
import com.example.assignment_kotlin.model.Story
import com.example.assignment_kotlin.utilities.personList
import com.example.assignment_kotlin.view.StoryAdapter

class MainActivity : AppCompatActivity() {
    private val TAG: String = "Main Activity"
    private lateinit var queue: RequestQueue

    private lateinit var storyView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)

        storyView = findViewById(R.id.storyView)

        loadStoryRecyclerView()
    }

    private fun loadStoryRecyclerView() {
        storyView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val data = ArrayList<Story>()
        for (person in personList) {
            data.add(Story(person.image, person.name))
        }

        val adapter = StoryAdapter(data, applicationContext, window)
        storyView.adapter = adapter
    }
}