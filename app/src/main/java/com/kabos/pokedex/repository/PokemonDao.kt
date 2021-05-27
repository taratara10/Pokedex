package com.kabos.pokedex.repository

import androidx.room.Dao
import androidx.room.Query
import com.kabos.pokedex.model.Pokemon

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonById(id:Int): Pokemon
}