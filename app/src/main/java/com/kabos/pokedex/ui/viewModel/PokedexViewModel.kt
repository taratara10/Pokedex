package com.kabos.pokedex.ui.viewModel

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

    var pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var pokemonNumber:Int = 0

    //country毎にfetchしたやつをListで保持する

    init {
        getPokemonList()
    }

    //listの初期化
    private fun initCountry(){

    }

    private fun getPokemonList(){
        //for で回す　[150回すのは無理なので、limit=30]

        //getPokemon(id,limit=20)

    }

    private fun getPokemon(id: Int) = viewModelScope.launch {
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

        //Listへinsertの処理
    }



}