package com.kabos.pokedex.repository

import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.PokemonInfo
import com.kabos.pokedex.model.PokemonSpecies
import retrofit2.Response
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokeApiService: PokeApiService){

    suspend fun getPokemonInfoById(id: Int):Response<PokemonInfo> =
        pokeApiService.getPokemonInfoById(id)

    suspend fun getPokemonSpeciesById(id: Int): Response<PokemonSpecies> =
        pokeApiService.getPokemonSpeciesById(id)

    fun mergePokemonData(info: PokemonInfo, species: PokemonSpecies): Pokemon {
        val typeSize = info.types.size
        val flavorText = species.flavor_text_entries.findLast {
            it.language.name == "ja-Hrkt"
        }//実際はversion.name == "sword"にしたいけど、未登場ポケモンがnullなので、findLastで最新の説明を引用

        //typeが1つならtype_twoはnull
        return if (typeSize == 1) Pokemon(
            id = species.id,
            name = species.names[0].name,
            genera = species.genera[0].genus,
            weight = info.weight,
            height = info.height,
            flavor_text = flavorText!!.flavor_text,
            sprite = info.sprites.front_default,
            type_one = info.types[0].type.name,
            type_two = null
        )
        else Pokemon(
            id = species.id,
            name = species.names[0].name,
            genera = species.genera[0].genus,
            weight = info.weight,
            height = info.height,
            flavor_text = flavorText!!.flavor_text,
            sprite = info.sprites.front_default,
            type_one = info.types[0].type.name,
            type_two = info.types[1].type.name
        )
    }

}