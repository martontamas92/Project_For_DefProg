package com.example.qrcodescanner.models

import org.json.JSONObject

class MySubject
{
    var id: Int?             = null
    var name: String?        = null
    var teacher: String?     = null
    var totalPresence: Int?  = null
    var myPresence: Int?     = null

    constructor(){}

    constructor( jsonObject: JSONObject)
    {

    }
    constructor( id: Int, name: String, teacher: String, totalPresence: Int, myPresence: Int )
    {
        this.id             = id
        this.name           = name
        this.teacher        = teacher
        this.totalPresence  = totalPresence
        this.myPresence     = myPresence
    }
}