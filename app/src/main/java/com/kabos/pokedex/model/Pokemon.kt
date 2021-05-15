package com.kabos.pokedex.model

data class Pokemon (
    val id: Int,
    val name: String,
    val weight: Float,
    val height: Float,
    val sprites: Sprites
)

data class Sprites (
    val front_default: String?,
    val front_shiny: String?
)