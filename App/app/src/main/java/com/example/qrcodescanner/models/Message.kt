package com.example.qrcodescanner.models

import org.json.JSONObject

class Message
{
    var message: String? = null

    constructor()

    constructor(result:String)
    {
        val jsonObject  = JSONObject(result)
        this.message    = jsonObject.getString( "message" )
    }
}