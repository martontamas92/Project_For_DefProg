package com.example.qrcodescanner.models

class Subject( id: Int, name: String, teacher: String )
{
    var id: Int?         = null
    var name: String?    = null
    var teacher: String? = null

    init
    {
        this.id         = id
        this.name       = name
        this.teacher    = teacher
    }
}