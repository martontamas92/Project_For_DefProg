package com.example.qrcodescanner.models

import android.util.Log
import org.json.JSONObject
import java.lang.Exception

class MySubject
{
    var name: String?        = null
    var totalPresence: Int?  = null
    var myPresence: Int?     = null

    constructor()

    constructor( jsonObject: JSONObject )
    {
        try
        {
            this.name           = jsonObject.getString( "subject_name" )
            this.totalPresence  = jsonObject.getInt( "all" )
            this.myPresence     = jsonObject.getInt( "attended" )
        }
        catch ( e: Exception)
        {
            Log.e( "my_subject_model_error", e.message )
        }
    }

    constructor( name: String, totalPresence: Int, myPresence: Int )
    {
        this.name           = name
        this.totalPresence  = totalPresence
        this.myPresence     = myPresence
    }
}