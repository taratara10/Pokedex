package com.kabos.pokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//成形後のデータ
@Entity(tableName = "pokemons")
data class Pokemon(
        @PrimaryKey val id: Int,
        val name: String,
        val genera: String,
        val weight: Float,
        val height: Float,
        val flavor_text: String,
        val sprite: String,
        val type_one: TypeData?,
        val type_two: TypeData? = null
)
//id, name, genera, weight, height, type, description, sprite,
