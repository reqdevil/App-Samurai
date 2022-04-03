package com.example.assignment_kotlin.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_kotlin.R
import com.example.assignment_kotlin.model.Story
import com.example.assignment_kotlin.utilities.personList
import com.example.assignment_kotlin.view.StoryListAdapter
import com.example.assignment_kotlin.view.StoryRecyclerAdapter

class MainActivity : AppCompatActivity() {
    private val TAG: String = "Main Activity"

    private lateinit var storyRecylerView: RecyclerView
    private lateinit var storyListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storyRecylerView = findViewById<RecyclerView>(R.id.storyRecyclerView)
        storyListView = findViewById(R.id.storyListView)

        loadStoryRecyclerView()
        loadStoryListView()
    }

    private fun loadStoryRecyclerView() {
        storyRecylerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val data = ArrayList<Story>()
        for (person in personList) {
            data.add(Story(person.image, person.name))
        }

        val adapter = StoryRecyclerAdapter(data)
        storyRecylerView.adapter = adapter
    }

    private fun loadStoryListView() {
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        storyListView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        Log.e(TAG, storyListView.layoutDirection.toString())
        storyListView.adapter = StoryListAdapter(this, personList)
    }
}