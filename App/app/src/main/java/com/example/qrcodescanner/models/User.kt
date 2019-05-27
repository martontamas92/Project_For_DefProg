package com.example.qrcodescanner.models

import android.util.Log
import org.json.JSONObject
import java.lang.Exception

class User
{
    var id: Int?            = null
    var firstName: String?  = null
    var middleName: String? = null
    var lastName: String?   = null
    var neptun: String?     = null
    var username: String?   = null

    constructor(){}

    constructor( result: String )
    {
        try
        {
            val jsonObject  = JSONObject( result )
            this.id         = jsonObject.getInt( "id" )
            this.neptun     = jsonObject.getJSONObject( "neptun" ).getString( "neptun" )
            val name        = jsonObject.getJSONObject( "name" )
            this.firstName  = name.getString( "firstName" )
            this.middleName = name.getString( "firstName" )
            this.lastName   = name.getString( "middleName" )
        }
        catch ( e: Exception )
        {
            Log.e( "error_user_model", "Error in user model" )
        }
    }

    constructor( id: Int, firstName: String, middleName: String, lastName: String, neptun: String, username: String )
    {
        this.id         = id
        this.firstName  = firstName
        this.middleName = middleName
        this.lastName   = lastName
        this.neptun     = neptun
        this.username   = username
    }
}