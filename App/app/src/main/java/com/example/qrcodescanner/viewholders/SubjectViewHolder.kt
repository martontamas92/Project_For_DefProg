package com.example.qrcodescanner.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.qrcodescanner.R

class SubjectViewHolder( itemView: View) : RecyclerView.ViewHolder( itemView )
{
    val name    = itemView.findViewById<TextView>( R.id.name )
    val major   = itemView.findViewById<TextView>( R.id.major )
    val teacher = itemView.findViewById<TextView>( R.id.teacher )
    val item    = itemView.findViewById<LinearLayout>( R.id.subject )
}