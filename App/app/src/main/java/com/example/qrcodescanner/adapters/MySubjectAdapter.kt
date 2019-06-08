package com.example.qrcodescanner.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.qrcodescanner.models.Subject
import com.example.qrcodescanner.R
import com.example.qrcodescanner.models.MySubject
import com.example.qrcodescanner.viewholders.MySubjectViewHolder
import com.example.qrcodescanner.viewholders.SubjectViewHolder

class MySubjectAdapter( private val mySubjectList: ArrayList<MySubject>  ) : RecyclerView.Adapter<MySubjectViewHolder>()
{
    override fun onCreateViewHolder( viewGroup: ViewGroup, position: Int ): MySubjectViewHolder
    {
        val view = LayoutInflater.from( viewGroup.context ).inflate( R.layout.my_subject_item, viewGroup, false )

        return MySubjectViewHolder( view )
    }

    override fun getItemCount(): Int
    {
        return mySubjectList.size
    }

    override fun onBindViewHolder( holder: MySubjectViewHolder, position: Int )
    {
        holder.name?.text           = mySubjectList[position].name
        holder.totalPresence?.text  = mySubjectList[position].totalPresence.toString()
        holder.myPresence?.text     = mySubjectList[position].myPresence.toString()

        var value = mySubjectList[position].myPresence!!.toDouble() / mySubjectList[position].totalPresence!!.toDouble()

        Log.i( "value", value.toString() )

        if( value <= 0.40 )
        {
            holder.myPresence?.setTextColor( Color.parseColor( "#D81B1B" ) )
        }
        else if( value < 0.70 && value >= 0.40 )
        {
            holder.myPresence?.setTextColor( Color.parseColor( "#FC9846" ) )
        }
        else
        {
            holder.myPresence?.setTextColor( Color.parseColor( "#00A004" ) )
        }
    }
}