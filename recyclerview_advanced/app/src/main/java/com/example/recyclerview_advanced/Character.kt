package com.example.recyclerview_advanced

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val location: Location,
    val image: String
)

data class Location(
    val name : String,
    val url: String
)
