package com.kabos.pokedex.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.LiveData
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.PokemonInfo
import com.kabos.pokedex.model.PokemonSpecies
import com.kabos.pokedex.util.NetworkBoundResource
import com.kabos.pokedex.util.Resource
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class PokemonRepository @Inject constructor(
        private val pokeApiService: PokeApiService,
        private val pokemonDb: PokemonDatabase){

    private val pokemonDao = pokemonDb.pokemonDao()

    suspend fun getPokemonInfoById(id: Int):Resource<Response<PokemonInfo>> =
            object :NetworkBoundResource<PokemonInfo, Response<PokemonInfo>>{
                override suspend fun queryFromDb(): PokemonInfo {
                    pokemonDao.getPokemonById(id)
                }
            }


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