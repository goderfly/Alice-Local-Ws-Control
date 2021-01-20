package com.mirbor.models

data class Response (
        var devices : List<Device>,
        var status : String
)

data class Config (
        var name : String
)

data class Device (
        var activation_code : Int,
        var activation_region : String,
        var config : Config,
        var glagol : Glagol,
        var id : String,
        var name : String,
        var platform : String,
        var promocode_activated : Boolean,
        var tags : List<String>
)

data class Security (
        var server_certificate : String,
        var server_private_key : String
)

data class Glagol (
        var security : Security
)