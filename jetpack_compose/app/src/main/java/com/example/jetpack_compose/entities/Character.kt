package com.example.jetpack_compose.entities

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val location: Location,
    val image: String,
    val episode: List<String>,
)

data class Location(
    val id: Int,
    val name : String,
    val url: String,
    val type: String,
    val dimension: String,
    val created: String
)
