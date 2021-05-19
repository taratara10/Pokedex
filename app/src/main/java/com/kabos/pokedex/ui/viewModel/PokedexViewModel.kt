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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel(){

    //todo PokeListの中に1~8のLiveDataListを格納する
    var pokemonListOne: MutableLiveData<List<Pokemon>> = MutableLiveData() //Kanto
    var pokemonListTwo: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var pokemonListThree: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var pokemonListFour: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var pokemonListFive: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var pokemonListSix: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var pokemonListSeven: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var pokemonListEight: MutableLiveData<List<Pokemon>> = MutableLiveData()

    var pokemonNumber:Int = 1

    //country毎にfetchしたやつをListで保持する

    init {
        getPokemonList()
    }

    private fun selectCountry(){

    }

    private fun getPokemonList(){
        for (i in pokemonNumber..pokemonNumber+30) {
            getPokemon(i,pokemonListOne)
        }
        Log.d("getPokemonList"," $pokemonListOne")
    }

    private fun getPokemon(id: Int, pokemonList:MutableLiveData<List<Pokemon>>) = viewModelScope.launch {
        val pokemonInfo = async {
            try {
                val request = repository.getPokemonInfoById(id)
                if(request.isSuccessful) request.body() else null //todo null怖い
            } catch (e: Exception){
                e.stackTrace
            }
        }

        val pokemonSpecies = async {
            try {
                val request = repository.getPokemonSpeciesById(id)
                if(request.isSuccessful) request.body() else null //todo null怖い
            } catch (e: Exception){
                e.stackTrace
            }
        }

        val pokemon = repository.mergePokemonData(
                pokemonInfo.await() as PokemonInfo,
                pokemonSpecies.await() as PokemonSpecies
                )

        pokemonList.postValue(listOf(pokemon))
    }



}