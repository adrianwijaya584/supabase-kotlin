package com.adrian.supabase.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adrian.supabase.R
import com.adrian.supabase.models.Cats

private val TAG= "catsAdapter"

class CatsAdapter(private val data: List<Cats>):
  RecyclerView.Adapter<CatsAdapter.ViewHolder>()
{

  class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val text: TextView

    init {
      text= view.findViewById(R.id.textView)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.text_row_item, parent, false)

    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.text.text= data[position].name;
  }

  override fun getItemCount()= data.size
}