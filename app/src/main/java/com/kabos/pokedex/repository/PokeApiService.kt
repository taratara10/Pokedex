package com.kabos.pokedex.repository

import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.PokemonInfo
import com.kabos.pokedex.model.PokemonSpecies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{id}")
    suspend fun getPokemonInfoById(@Path("id") id: Int): Response<PokemonInfo>

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpeciesById(@Path("id") id: Int): Response<PokemonSpecies>
}