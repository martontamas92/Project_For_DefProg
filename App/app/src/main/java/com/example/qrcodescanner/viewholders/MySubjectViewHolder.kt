package com.example.qrcodescanner.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.qrcodescanner.R

class MySubjectViewHolder(itemView: View) : RecyclerView.ViewHolder( itemView )
{
    val name            = itemView.findViewById<TextView>( R.id.name )
    val totalPresence   = itemView.findViewById<TextView>( R.id.total_presence )
    val myPresence      = itemView.findViewById<TextView>( R.id.my_presence )
}