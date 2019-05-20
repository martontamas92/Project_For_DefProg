package com.example.qrcodescanner.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.qrcodescanner.R
import kotlinx.android.synthetic.main.subject_item.view.*

class SubjectViewHolder( itemView: View) : RecyclerView.ViewHolder( itemView )
{
    val name    = itemView.findViewById<TextView>( R.id.name )
    val teacher = itemView.findViewById<TextView>( R.id.teacher )
}