package com.example.assignment_kotlin.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_kotlin.R
import com.example.assignment_kotlin.controller.StoryActivity
import com.example.assignment_kotlin.model.Story
import com.example.assignment_kotlin.service.DataService
import com.example.assignment_kotlin.service.ToastService
import com.example.assignment_kotlin.utilities.ToastType
import java.io.ByteArrayOutputStream

class StoryAdapter(private val list: List<Story>, private val context: Context, private val window: Window) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    private val TAG = "Story Adapter"

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressIndicator)
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
            holder.progressBar.visibility = View.VISIBLE

            val beginTime = System.currentTimeMillis()
            DataService.getInstance(context).getImage(TAG, completion = { bitmap, error ->
                if (error != null) {
                    Log.e(TAG, error.localizedMessage!!)

                    ToastService.instance.showToast(
                        window,
                        context,
                        ToastType.ERROR,
                        error.localizedMessage!!
                    )

                    return@getImage
                }

                StoryActivity.bitmap = bitmap!!
                val intent = Intent(context, StoryActivity::class.java)

                intent.putExtra("beginTime", beginTime)
                intent.putExtra("profilePicture", story.image)
                intent.putExtra("profileName", story.text)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                holder.progressBar.visibility = View.INVISIBLE
                context.startActivity(intent)
            })
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
