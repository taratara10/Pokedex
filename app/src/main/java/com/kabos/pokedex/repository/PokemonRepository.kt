package com.kabos.pokedex.repository

import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.PokemonInfo
import com.kabos.pokedex.model.PokemonSpecies
import retrofit2.Response
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokeApiService: PokeApiService){

    suspend fun getPokemonInfoById(id: Int):Response<Pokemon> =
        pokeApiService.getPokemonInfoById(id)

    suspend fun getPokemonSpeciesById(id: Int): Response<PokemonSpecies> =
        pokeApiService.getPokemonSpeciesById(id)

    fun mergePokemonData(info: PokemonInfo, species: PokemonSpecies): Pokemon =
            Pokemon(
                species.id,
                species.names[0].name,
                species.genera[0].genus,
                info.weight,
                info.height,
                species.flavor_text_entries[0].flavor_text, //todo 0じゃないので修正
                info.sprites.front_default,
                info.types[0].type.name,
                info.types[1].type.name
            )

}