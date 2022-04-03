package com.example.assignment_kotlin.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.assignment_kotlin.R
import com.example.assignment_kotlin.model.Person
import com.example.assignment_kotlin.model.Story

class StoryListAdapter(private val context: Context,
                       private val list: List<Person>
) : BaseAdapter() {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.recycler_view_story, parent, false)

        val imageView = rowView.findViewById(R.id.imageView) as ImageView
        val textView = rowView.findViewById(R.id.textView) as TextView

        val person = getItem(position) as Person
        textView.text = person.name
        imageView.setImageResource(person.image)

        return rowView
    }
}