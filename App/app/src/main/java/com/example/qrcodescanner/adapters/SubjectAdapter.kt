package com.example.qrcodescanner.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.qrcodescanner.models.Subject
import com.example.qrcodescanner.R
import com.example.qrcodescanner.viewholders.SubjectViewHolder

class SubjectAdapter( private val subjectList: ArrayList<Subject>  ) : RecyclerView.Adapter<SubjectViewHolder>()
{
    override fun onCreateViewHolder( viewGroup: ViewGroup, position: Int ): SubjectViewHolder
    {
        val view = LayoutInflater.from( viewGroup.context ).inflate( R.layout.subject_item, viewGroup, false )

        return SubjectViewHolder( view )
    }

    override fun getItemCount(): Int
    {
        return subjectList.size
    }

    override fun onBindViewHolder( holder: SubjectViewHolder, position: Int )
    {
        holder.name?.text       = subjectList[position].name
        holder.teacher?.text    = subjectList[position].teacher
    }
}