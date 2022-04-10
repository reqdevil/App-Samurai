package com.example.assignment_kotlin.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_kotlin.R
import com.example.assignment_kotlin.controller.StoryActivity
import com.example.assignment_kotlin.model.Story
import com.example.assignment_kotlin.service.DataService
import java.io.ByteArrayOutputStream

class StoryAdapter(private val list: List<Story>, private val context: Context) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    private val TAG = "Story Adapter"

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_story, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = list[position]

        holder.imageView.setImageResource(story.image)
        holder.textView.text = story.text

        holder.imageView.setOnClickListener {
            val intent = Intent(context, StoryActivity::class.java)
            intent.putExtra("position", position)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
