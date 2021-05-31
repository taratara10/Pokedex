package com.kabos.pokedex.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.PokemonInfo
import com.kabos.pokedex.model.PokemonSpecies
import com.kabos.pokedex.model.Type
import com.kabos.pokedex.util.*

@Database(entities = [Pokemon::class,
        PokemonInfo::class,
        PokemonSpecies::class],
        version = 1)
@TypeConverters(SpritesConverter::class,
        TypesConverter::class,
        NameConverter::class,
        FlavorTextEntryConverter::class,
        GeneraConverter::class)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}