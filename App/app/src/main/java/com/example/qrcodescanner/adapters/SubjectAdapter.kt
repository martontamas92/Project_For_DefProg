package com.example.qrcodescanner.adapters

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.qrcodescanner.MyApplication
import com.example.qrcodescanner.models.Subject
import com.example.qrcodescanner.R
import com.example.qrcodescanner.activities.SubjectActivity
import com.example.qrcodescanner.models.Message
import com.example.qrcodescanner.viewholders.SubjectViewHolder
import kotlinx.android.synthetic.main.activity_subject.progress_bar
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class SubjectAdapter( private val subjectList: ArrayList<Subject>, private val activity: SubjectActivity )
    : RecyclerView.Adapter<SubjectViewHolder>()
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
        holder.name?.text    = subjectList[position].name
        holder.major?.text   = subjectList[position].major
        holder.teacher?.text = subjectList[position].teacher

        holder.item.setOnClickListener {
            val builder = AlertDialog.Builder( activity )

            builder.setTitle( activity.resources.getString( R.string.add_subject_title ) )
            builder.setMessage( subjectList[position].name + activity.resources.getString( R.string.add_subject_message ) )
            builder.setPositiveButton( activity.resources.getString( R.string.yes ) ){dialog, which ->
                activity.progress_bar.visibility    = View.VISIBLE

                mySubjectsAsync( subjectList[position].id, position )
            }
            builder.setNegativeButton(activity.resources.getString( R.string.no ) ){dialog,which ->

            }

            val dialog: AlertDialog = builder.create()

            dialog.show()
        }
    }

    private fun mySubjectsAsync(id: Int?,position: Int)
    {
        val client          = OkHttpClient()
        val token           = MyApplication.instance.bearerToken
        val url             = URL(MyApplication.URL + MyApplication.SUBJECTATTEND )
        val json            = MediaType.get( "application/json; charset=utf-8" )
        val body            = RequestBody.create( json, createJsonForAttendSubject( id ).toString() )
        val request         = Request.Builder()
            .addHeader("Authorization", "Bearer $token")
            .url( url)
            .post( body )
            .build()

        client.newCall( request ).enqueue( object : Callback
        {
            override fun onFailure( call: Call, e: IOException ){}

            override fun onResponse( call: Call, response: Response )
            {
                val jsonData    = response.body()!!.string()
                val message     = Message( jsonData )

                if ( !response.isSuccessful )
                {
                    Toast.makeText( activity.applicationContext,message.message, Toast.LENGTH_SHORT  ).show()

                    activity.progress_bar.visibility = View.GONE

                    return
                }

                Toast.makeText( activity.applicationContext,message.message, Toast.LENGTH_SHORT  ).show()

                activity.runOnUiThread {
                    removeItemInList( position )

                    activity.progress_bar.visibility = View.GONE
                }
            }
        })
    }

    fun removeItemInList(position: Int)
    {
        subjectList.removeAt(position)
        notifyDataSetChanged()
    }

    fun createJsonForAttendSubject(id: Int?): JSONObject
    {
        val userId      = MyApplication.instance.user.id
        val jsonObject  = JSONObject(
            """{
                |"st_id":$userId,
                |"sj_id":$id
                |}""".trimMargin()
        )
        return jsonObject
    }
}