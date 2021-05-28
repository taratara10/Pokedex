package com.kabos.pokedex.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kabos.pokedex.model.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}