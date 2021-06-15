package com.kabos.pokedex.repository

import android.util.Log
import com.kabos.pokedex.model.*
import com.kabos.pokedex.util.NetworkBoundResource
import com.kabos.pokedex.util.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PokemonRepository @Inject constructor(
        private val pokeApiService: PokeApiService,
        private val pokemonDb: PokemonDatabase){

    private val pokemonDao = pokemonDb.pokemonDao()

    suspend fun getPokemonInfoById(
                id: Int,
                onFetchFailed: (Throwable) -> Unit
            ): Resource<PokemonInfo>? {
        val pokemonInfoData = object : NetworkBoundResource<PokemonInfo, Response<PokemonInfo>>() {
            override suspend fun queryFromDb(): PokemonInfo =
                pokemonDao.getPokemonInfoById(id)

            override suspend fun fetchFromNetwork(): Response<PokemonInfo> =
                pokeApiService.getPokemonInfoById(id)

            override suspend fun saveFetchResult(fetchData: Response<PokemonInfo>) {
                fetchData.body()?.let { pokemonDao.insertPokemonInfo(it) }
            }

            override fun onFetchFailed(t: Throwable) {
                if (t !is HttpException && t !is IOException) throw t
                onFetchFailed(t)
            }
        }
        pokemonInfoData.execute()
        return pokemonInfoData.result
    }


    suspend fun getPokemonSpeciesById(
            id: Int,
            onFetchFailed: (Throwable) -> Unit
    ): Resource<PokemonSpecies>? {
        val pokemonSpeciesData =  object : NetworkBoundResource<PokemonSpecies, Response<PokemonSpecies>>() {
            override suspend fun queryFromDb(): PokemonSpecies =
                    pokemonDao.getPokemonSpeciesById(id)

            override suspend fun fetchFromNetwork(): Response<PokemonSpecies> =
                    pokeApiService.getPokemonSpeciesById(id)

            override suspend fun saveFetchResult(fetchData: Response<PokemonSpecies>) {
                fetchData.body()?.let { pokemonDao.insertPokemonSpecies(it) }
            }

            override fun onFetchFailed(t: Throwable) {
                if (t !is HttpException && t !is IOException) throw t
                onFetchFailed(t)
            }
        }
        pokemonSpeciesData.execute()
        return pokemonSpeciesData.result
    }

    fun mergePokemonData(info: PokemonInfo, species: PokemonSpecies): Pokemon {
        val typeSize = info.types.size
        val flavorTextEntry = species.flavor_text_entries.findLast {
            it.language.name == "ja-Hrkt"
        }//実際はversion.name == "sword"にしたいけど、未登場ポケモンがnullなので、findLastで最新の説明を引用
        val flavorText = flavorTextEntry!!.flavor_text.replace("\n","")


        //typeが1つならtype_twoはnull
        return if (typeSize == 1) Pokemon(
            id = species.id,
            name = species.names[0].name,
            genera = species.genera[0].genus,
            weight = info.weight,
            height = info.height,
            flavor_text = flavorText,
            sprite = info.sprites.front_default,
            type_one = convertTypeToTypeImage(info.types[0].type.name),
            type_two = null
        )
        else Pokemon(
            id = species.id,
            name = species.names[0].name,
            genera = species.genera[0].genus,
            weight = info.weight,
            height = info.height,
            flavor_text = flavorText,
            sprite = info.sprites.front_default,
            type_one = convertTypeToTypeImage(info.types[0].type.name),
            type_two = convertTypeToTypeImage(info.types[1].type.name)
        )
    }

    private fun convertTypeToTypeImage(type: String): TypeData? {
        return when(type) {
            "bug" -> TypeData.Bug
            "dark" -> TypeData.Dark
            "dragon" -> TypeData.Dragon
            "electric" -> TypeData.Electric
            "fairy" -> TypeData.Fairy
            "fighting" -> TypeData.Fighting
            "fire" -> TypeData.Fire
            "flying" -> TypeData.Flying
            "ghost" -> TypeData.Ghost
            "grass" -> TypeData.Grass
            "ground" -> TypeData.Ground
            "ice" -> TypeData.Ice
            "normal" -> TypeData.Normal
            "poison" -> TypeData.Poison
            "psychic" -> TypeData.Psychic
            "rock" -> TypeData.Rock
            "steel" -> TypeData.Steel
            "water" -> TypeData.Water
            else -> null
        }
    }



}
