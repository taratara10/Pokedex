package com.kabos.pokedex.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.PokemonInfo
import com.kabos.pokedex.model.PokemonSpecies

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemons WHERE id = :id")
    suspend fun getPokemonById(id:Int): Pokemon

    @Query( "SELECT * FROM pokemon_info WHERE id = :id")
    suspend fun getPokemonInfoById(id: Int) :PokemonInfo

    @Query( "SELECT * FROM pokemon_species WHERE id = :id")
    suspend fun getPokemonSpeciesById(id: Int) :PokemonSpecies

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInfo(pokemonInfo: PokemonInfo)

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonSpecies(pokemonSpecies: PokemonSpecies)
}