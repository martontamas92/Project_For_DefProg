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

    }

    constructor( id: Int, name: String, teacher: String )
    {
        this.id         = id
        this.name       = name
        this.teacher    = teacher
    }
}