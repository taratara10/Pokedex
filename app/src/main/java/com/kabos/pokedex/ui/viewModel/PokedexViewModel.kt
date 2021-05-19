package com.kabos.pokedex.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.PokemonInfo
import com.kabos.pokedex.model.PokemonSpecies
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

    private fun selectCountry(){
        //when 1-> kanto とかで、MainLiveDataにセット

    }

    fun getPokemonList()= viewModelScope.launch(Dispatchers.IO) {
        //if (list.isEmpty()) -> forループで取得
        for (i in pokemonNumber..pokemonNumber + 30) {
            val pokemonInfo: Deferred<Any?> = async {
                try {
                    val request = repository.getPokemonInfoById(i)
                    if (request.isSuccessful) request.body() else null //todo null怖い
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            val pokemonSpecies: Deferred<Any?> = async {
                try {
                    val request = repository.getPokemonSpeciesById(i)
                    if (request.isSuccessful) request.body() else null //todo null怖い
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            val pokemon = repository.mergePokemonData(
                pokemonInfo.await() as PokemonInfo,
                pokemonSpecies.await() as PokemonSpecies
            )

            pokemonListOne.add(pokemon)
        }
        pokemonList.postValue(pokemonListOne)
        Log.d("getPokemonMutableList", " $pokemonListOne")


    }


}