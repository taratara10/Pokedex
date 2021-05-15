package com.kabos.pokedex.repository

import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.PokemonSpecies
import retrofit2.Response
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokeApiService: PokeApiService){

    suspend fun getPokemonById(id: Int):Response<Pokemon> =
        pokeApiService.getPokemonById(id)

    suspend fun getPokemonSpeciesById(id: Int): Response<PokemonSpecies> =
        pokeApiService.getPokemonSpeciesById(id)
}