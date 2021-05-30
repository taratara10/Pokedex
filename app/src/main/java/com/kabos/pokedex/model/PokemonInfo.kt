package com.kabos.pokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_info")
data class PokemonInfo (
        @PrimaryKey val id: Int,
        val name: String,
        val weight: Float,
        val height: Float,
        val sprites: Sprites,
        val types: List<Types>
)

data class Sprites (
    val front_default: String,
    val front_shiny: String?
)
data class Types(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String
)
//統合データクラスで取り扱ったほうが楽かも

