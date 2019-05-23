package com.example.qrcodescanner.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.example.qrcodescanner.R
import com.example.qrcodescanner.adapters.MySubjectAdapter
import com.example.qrcodescanner.adapters.SubjectAdapter
import com.example.qrcodescanner.models.MySubject

class MySubjectsActivity : AppCompatActivity()
{

    private lateinit var recyclerView           : RecyclerView
    private lateinit var recyclerViewAdapter    : MySubjectAdapter
    var mySubjectList                           = ArrayList<MySubject>()

    override fun onCreate( savedInstanceState: Bundle? )
    {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_my_subjects )
        loadInputsAndVariables()

        recyclerView.layoutManager = LinearLayoutManager( this, LinearLayout.VERTICAL, false )

        loadToolbar()
    }

    private fun loadInputsAndVariables()
    {
        recyclerView = findViewById(R.id.recyclerView)

        mySubjectList.add( MySubject( 1, "Programozas 2", "Szanto Zoltan", 20, 5 ) )
        mySubjectList.add( MySubject( 2, "Parhuzamos Program", "Icalanzan David", 20, 15 ) )
        mySubjectList.add( MySubject( 3, "Informatika alapjai", "Olah Gal Robert", 20, 10 ) )
        mySubjectList.add( MySubject( 4, "Informatika alapjai", "Olah Gal Robert", 20, 17 ) )
        mySubjectList.add( MySubject( 5, "Informatika alapjai", "Olah Gal Robert", 20, 8 ) )

        recyclerViewAdapter     = MySubjectAdapter( mySubjectList )
        recyclerView.adapter    = recyclerViewAdapter
    }

    private fun loadToolbar()
    {
        val toolbar: Toolbar = findViewById( R.id.toolbar_subject )

        setSupportActionBar( toolbar )
    }
}
