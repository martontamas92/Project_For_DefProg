package com.example.qrcodescanner.models

import android.util.Log
import org.json.JSONObject
import java.lang.Exception

class Subject
{
    var id: Int?         = null
    var name: String?    = null
    var teacher: String? = null
    var major: String? = null

    constructor()

    constructor( result: String )
    {
        try
        {
            val jsonObject  = JSONObject( result )
            this.id         = jsonObject.getInt( "id" )
            this.name       = jsonObject.getString( "subjectName" )
            this.teacher    = jsonObject.getString( "name" )
            this.major      = jsonObject.getString( "subjectMajor" )
        }
        catch ( e: Exception )
        {
            Log.e( "subject_model_error", e.message)
        }
    }

    constructor( jsonObject: JSONObject )
    {
        try
        {
            this.id         = jsonObject.getInt( "id" )
            this.name       = jsonObject.getString( "subjectName" )
            this.teacher    = jsonObject.getString( "name" )
            this.major    = jsonObject.getString( "subjectMajor" )
        }
        catch ( e: Exception)
        {
            Log.e( "subject_model_error", e.message)
        }
    }

    constructor( id: Int, name: String, teacher: String, major: String )
    {
        this.id         = id
        this.name       = name
        this.teacher    = teacher
        this.major      = major
    }
}