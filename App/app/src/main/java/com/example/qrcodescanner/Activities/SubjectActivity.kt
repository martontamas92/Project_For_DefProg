package com.example.qrcodescanner.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.qrcodescanner.Adapters.SubjectAdapter
import com.example.qrcodescanner.Models.Subject
import com.example.qrcodescanner.R

class SubjectActivity : AppCompatActivity()
{
    private lateinit var recyclerView           : RecyclerView
    private lateinit var recyclerViewAdapter    : SubjectAdapter
    var subjectList                             = ArrayList<Subject>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)
        loadInputsAndVariables()

        recyclerView.layoutManager = LinearLayoutManager( this, LinearLayout.VERTICAL, false )
    }

    private fun loadInputsAndVariables()
    {
        recyclerView = findViewById(R.id.recyclerView)

        subjectList.add( Subject( 1, "Programozas 2", "Szanto Zoltan" ) )
        subjectList.add( Subject( 2, "Parhuzamos Programozas", "Icalanzan David" ) )
        subjectList.add( Subject( 3, "Informatika alapjai", "Olah Gal Robert" ) )

        recyclerViewAdapter     = SubjectAdapter( subjectList )
        recyclerView.adapter    = recyclerViewAdapter
    }
}
