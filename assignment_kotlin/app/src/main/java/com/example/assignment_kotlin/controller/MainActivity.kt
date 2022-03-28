package com.example.assignment_kotlin.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_kotlin.R
import com.example.assignment_kotlin.model.Story
import com.example.assignment_kotlin.view.StoryAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var storyView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storyView = findViewById<RecyclerView>(R.id.storyView)
        storyView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        loadStoryView()
    }

    private fun loadStoryView() {
        val data = ArrayList<Story>()
        for (i in 1..20) {
            data.add(Story(R.drawable.logo, "Item $i"))
        }

        val adapter = StoryAdapter(data)
        storyView.adapter = adapter
    }
}