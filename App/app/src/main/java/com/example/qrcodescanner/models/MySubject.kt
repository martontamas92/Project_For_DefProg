package com.example.qrcodescanner.models

import android.util.Log
import org.json.JSONObject
import java.lang.Exception

class MySubject
{
    var name: String?        = null
    var teacher: String?     = null
    var totalPresence: Int?  = null
    var myPresence: Int?     = null

    constructor()

    constructor( jsonObject: JSONObject)
    {
        try
        {
            this.name           = jsonObject.getString( "subjectName" )
            this.teacher        = jsonObject.getString( "name" )
            this.totalPresence  = jsonObject.getInt( "totalPresence" )
            this.myPresence     = jsonObject.getInt( "myPresence" )
        }
        catch ( e: Exception)
        {
            Log.e( "my_subject_model_error", e.message)
        }
    }
    constructor( name: String, teacher: String, totalPresence: Int, myPresence: Int )
    {
        this.name           = name
        this.teacher        = teacher
        this.totalPresence  = totalPresence
        this.myPresence     = myPresence
    }
}