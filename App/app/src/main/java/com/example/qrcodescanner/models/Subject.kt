package com.example.qrcodescanner.models

import org.json.JSONObject

class Subject
{
    var id: Int?         = null
    var name: String?    = null
    var teacher: String? = null

    constructor(){}

    constructor( jsonObject: JSONObject )
    {
        this.id         = jsonObject.getInt( "id" )
        this.name       = jsonObject.getString( "name" )
        this.teacher    = jsonObject.getString( "teacher" )
    }

    constructor( id: Int, name: String, teacher: String )
    {
        this.id         = id
        this.name       = name
        this.teacher    = teacher
    }
}