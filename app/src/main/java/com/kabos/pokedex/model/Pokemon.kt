package com.kabos.pokedex.model

//成形後のデータ
data class Pokemon(
    val id: Int,
    val name: String,
    val genera: String,
    val weight: Float,
    val height: Float,
    val flavor_text: String,
    val sprites: String,
    val type_one: String,
    val type_two: String?
)
//id, name, genera, weight, height, type, description, sprite,
