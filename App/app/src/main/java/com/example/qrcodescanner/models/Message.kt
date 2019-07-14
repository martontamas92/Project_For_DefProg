package com.example.qrcodescanner.models

import android.util.Log
import org.json.JSONObject

class Message
{
    var message: String? = null

    constructor()

    constructor( result:String )
    {
        try
        {
            val jsonObject  = JSONObject( result )
            this.message    = jsonObject.getString( "message" )
        }
        catch ( e : Exception )
        {
            Log.e( "message_model_error", e.message )
        }
    }
}