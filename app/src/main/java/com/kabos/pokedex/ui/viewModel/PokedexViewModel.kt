package com.kabos.pokedex.ui.viewModel

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
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel(){

    //todo PokeMutableListの中に1~8のLiveDataMutableListを格納する
    var pokemonList:MutableLiveData<List<Pokemon>> = MutableLiveData()
    var dialogPokemon: Pokemon? = null
    var currentRegion: MutableLiveData<Region> = MutableLiveData(Region.Kanto)

    var listKanto: MutableList<Pokemon> = mutableListOf()
    var listJohto: MutableList<Pokemon> = mutableListOf()
    var listHoenn: MutableList<Pokemon> = mutableListOf()
    var listSinnoh: MutableList<Pokemon> = mutableListOf()
    var listUnova: MutableList<Pokemon> = mutableListOf()
    var listKalos: MutableList<Pokemon> = mutableListOf()
    var listAlola: MutableList<Pokemon> = mutableListOf()
    var listGalar: MutableList<Pokemon> = mutableListOf()

    var currentNumber:Int = 1 //表示されるポケモンの図鑑番号
    var regionStartNumber:Int = 1
    var regionEndNumber: Int = 151
    //country毎にfetchしたやつをMutableListで保持する

    init {
        getPokemonList()
    }


    fun updateRegion(region: Region) {
        //regionが異なる場合は、updateしてlistも更新する
        if (currentRegion.value == region) return
        else {
            //todo stop getPokemonList()
            currentRegion.postValue(region)
            regionStartNumber = region.start
            regionEndNumber = region.end
            currentNumber = regionStartNumber
            getPokemonList()
        }
    }

    fun updateDialogPokemon(pokemon:Pokemon) {
        dialogPokemon = pokemon
    }

    fun getPokemonList()= viewModelScope.launch {
        //todo save currentNum each region, also save recyclerView position
        //pokemonListがLiveDataで直接addできないので、一旦listRegionにaddして最後にpost
        for (i in 1..5) {
            if (currentNumber <= regionEndNumber){
                val pokemonInfo = getPokemonInfo(currentNumber)
                val pokemonSpecies = getPokemonSpecies(currentNumber)
                val pokemon = repository.mergePokemonData(
                        pokemonInfo.await() as PokemonInfo,
                        pokemonSpecies.await() as PokemonSpecies
                )
                getListByRegion(currentRegion.value!!).add(pokemon)
                currentNumber += 1
            }
        }
        pokemonList.postValue(getListByRegion(currentRegion.value!!))
    }

    private suspend fun getPokemonInfo(id: Int): Deferred<Any?> = withContext(Dispatchers.IO) {
        async {
            try {
                val request = repository.getPokemonInfoById(id)
                if (request.isSuccessful) request.body() else null //todo null怖い
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    private suspend fun getPokemonSpecies(id: Int): Deferred<Any?> = withContext(Dispatchers.IO) {
        async {
            try {
                val request = repository.getPokemonSpeciesById(id)
                if (request.isSuccessful) request.body() else null //todo null怖い
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    private fun getListByRegion(region: Region): MutableList<Pokemon> {
        return when(region){
            Region.Kanto -> listKanto
            Region.Johto -> listJohto
            Region.Hoenn -> listHoenn
            Region.Sinnoh -> listSinnoh
            Region.Unova -> listUnova
            Region.Kalos -> listKalos
            Region.Alola -> listAlola
            Region.Galar -> listGalar
        }
    }

}