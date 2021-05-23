package com.kabos.pokedex.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.PokemonInfo
import com.kabos.pokedex.model.PokemonSpecies
import com.kabos.pokedex.model.Region
import com.kabos.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel(){

    //todo PokeMutableListの中に1~8のLiveDataMutableListを格納する
    var pokemonList:MutableLiveData<List<Pokemon>> = MutableLiveData()
    var dialogPokemon: Pokemon? = null
    var currentRegion: MutableLiveData<Region> = MutableLiveData(Region.Kanto)

    var pokemonListOne: MutableList<Pokemon> = mutableListOf() //Kanto
    var pokemonListTwo: MutableList<Pokemon> = mutableListOf()
    var pokemonListThree: MutableList<Pokemon> = mutableListOf()
    var pokemonListFour: MutableList<Pokemon> = mutableListOf()
    var pokemonListFive: MutableList<Pokemon> = mutableListOf()
    var pokemonListSix: MutableList<Pokemon> = mutableListOf()
    var pokemonListSeven: MutableList<Pokemon> = mutableListOf()
    var pokemonListEight: MutableList<Pokemon> = mutableListOf()

    var pokemonNumber:Int = 1

    //country毎にfetchしたやつをMutableListで保持する

    init {
        getPokemonList()
    }


    fun updateRegion(region: Region) {
        //regionが異なる場合は、updateしてlistも更新する
        if (currentRegion.value == region) return
        else {
            currentRegion.postValue(region)
            getPokemonList()
        }
    }

    fun updateDialogPokemon(pokemon:Pokemon) {
        dialogPokemon = pokemon
    }



    fun getPokemonList()= viewModelScope.launch {

        //regionからpokemonNumをセットする処理


        //if (list.isEmpty()) -> forループで取得
        for (id in pokemonNumber..pokemonNumber + 5) {
            val pokemonInfo = getPokemonInfo(id)
            val pokemonSpecies = getPokemonSpecies(id)
            val pokemon = repository.mergePokemonData(
                            pokemonInfo.await() as PokemonInfo,
                            pokemonSpecies.await() as PokemonSpecies
                            )

            pokemonListOne.add(pokemon)
        }
        pokemonNumber += 6
        pokemonList.postValue(pokemonListOne)
    }

    suspend fun getPokemonInfo(id: Int): Deferred<Any?> = withContext(Dispatchers.IO) {
        async {
            try {
                val request = repository.getPokemonInfoById(id)
                if (request.isSuccessful) request.body() else null //todo null怖い
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    suspend fun getPokemonSpecies(id: Int): Deferred<Any?> = withContext(Dispatchers.IO) {
        async {
            try {
                val request = repository.getPokemonSpeciesById(id)
                if (request.isSuccessful) request.body() else null //todo null怖い
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

}