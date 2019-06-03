package com.example.qrcodescanner.adapters

import android.os.AsyncTask
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.qrcodescanner.MyApplication
import com.example.qrcodescanner.models.Subject
import com.example.qrcodescanner.R
import com.example.qrcodescanner.activities.SubjectActivity
import com.example.qrcodescanner.viewholders.SubjectViewHolder
import kotlinx.android.synthetic.main.activity_subject.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class SubjectAdapter( private val subjectList: ArrayList<Subject>,
                      private val activity: SubjectActivity )
    : RecyclerView.Adapter<SubjectViewHolder>()
{
    private var subjectAttendTask : SubjectAttendTask? = null

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
        holder.teacher?.text = subjectList[position].teacher

        holder.item.setOnClickListener {
            val builder = AlertDialog.Builder( activity )

            builder.setTitle( activity.resources.getString( R.string.add_subject_title ) )
            builder.setMessage( subjectList[position].name + activity.resources.getString( R.string.add_subject_message ) )
            builder.setPositiveButton( activity.resources.getString( R.string.yes ) ){dialog, which ->
                activity.progress_bar.visibility    = View.VISIBLE
                subjectAttendTask                   = SubjectAttendTask( subjectList[position].id!!, position )

                subjectAttendTask!!.execute( null as Void? )
            }
            builder.setNegativeButton(activity.resources.getString( R.string.no ) ){dialog,which ->

            }

            val dialog: AlertDialog = builder.create()

            dialog.show()
        }
    }

    inner class SubjectAttendTask internal constructor( private val id: Int, private val position: Int ) :
        AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            // TODO: attempt authentication against a network service.

            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                return false
            }

            return true
        }

        override fun onPostExecute( success: Boolean? )
        {
            subjectAttendTask = null

            if ( success!! )
            {
                doAsync{
                    val client          = OkHttpClient()
                    val url             = URL(MyApplication.URL + MyApplication.SUBJECTATTEND )
                    val json            = MediaType.get( "application/json; charset=utf-8" )
                    val body            = RequestBody.create( json, createJsonForAttendSubject( id ).toString() )
                    val request         = Request.Builder()
                        //.addHeader("Authorization", "Bearer $token")
                        .url( url)
                        .post( body )
                        .build()

                    val response = client.newCall( request ).execute()

                    uiThread{
                        Log.i( "response", response.isSuccessful.toString() )

                        if( !response.isSuccessful )
                        {
                            Toast.makeText( activity.applicationContext, R.string.error_registration, Toast.LENGTH_SHORT  ).show()

                            return@uiThread
                        }
                        else
                        {
                            subjectList.removeAt( position )
                            notifyDataSetChanged()

                            activity.progress_bar.visibility = View.GONE
                        }
                    }
                }
            }
            else
            {
                Toast.makeText( activity.applicationContext, R.string.error_registration, Toast.LENGTH_SHORT  ).show()
            }
        }

        override fun onCancelled()
        {
            subjectAttendTask = null
        }
    }

    private fun createJsonForAttendSubject( id: Int ): JSONObject
    {
        var userId      = MyApplication.instance.user.id
        var jsonObject  = JSONObject(
            """{
                |"st_id":$userId,
                |"sj_id":$id
                |}""".trimMargin()
        )
        return jsonObject
    }
}