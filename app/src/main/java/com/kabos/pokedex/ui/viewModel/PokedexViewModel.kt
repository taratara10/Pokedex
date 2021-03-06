package com.kabos.pokedex.ui.viewModel

import android.util.Log
import android.widget.Toast
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
    val pokemonList:MutableLiveData<List<Pokemon>> = MutableLiveData()
    var dialogPokemon: Pokemon? = null
    val currentRegion: MutableLiveData<Region> = MutableLiveData(Region.Kanto)

    var listKanto: MutableList<Pokemon> = mutableListOf()
    var listJohto: MutableList<Pokemon> = mutableListOf()
    var listHoenn: MutableList<Pokemon> = mutableListOf()
    var listSinnoh: MutableList<Pokemon> = mutableListOf()
    var listUnova: MutableList<Pokemon> = mutableListOf()
    var listKalos: MutableList<Pokemon> = mutableListOf()
    var listAlola: MutableList<Pokemon> = mutableListOf()
    var listGalar: MutableList<Pokemon> = mutableListOf()

    var positionKanto: Int = Region.Kanto.start
    var positionJohto: Int = Region.Johto.start
    var positionHoenn: Int = Region.Hoenn.start
    var positionSinnoh: Int = Region.Sinnoh.start
    var positionUnova: Int = Region.Unova.start
    var positionKalos: Int = Region.Kanto.start
    var positionAlola: Int = Region.Alola.start
    var positionGalar: Int = Region.Galar.start

    var currentNumber:Int = 1 //表示されるポケモンの図鑑番号
    var regionStartNumber:Int = 1
    var regionEndNumber: Int = 151

    var isLoading: Boolean = false

    init {
        getPokemonList()
    }


    fun updateRegion(region: Region) {
        //regionが異なる場合は、updateしてlistも更新する
        if (currentRegion.value == region) return
        else {
            isLoading = false
            currentRegion.postValue(region)
            regionStartNumber = region.start
            regionEndNumber = region.end
            currentNumber = getPositionByRegion(region)
            getPokemonList()
        }
    }

    fun updateDialogPokemon(pokemon:Pokemon) {
        dialogPokemon = pokemon
    }

    fun getPokemonList()= viewModelScope.launch {
        //check loading
        if (isLoading)return@launch else isLoading = true

        //Get pokemon after add currentRegionList
        val list = async{
            if (currentNumber <= regionEndNumber && isLoading) {
                val pokemonInfo = getPokemonInfo(currentNumber).await()
                val pokemonSpecies = getPokemonSpecies(currentNumber).await()
                if (pokemonInfo != null && pokemonSpecies != null) {
                    val pokemon = repository.mergePokemonData(
                            pokemonInfo as PokemonInfo,
                            pokemonSpecies as PokemonSpecies)
                    getListByRegion(currentRegion.value!!).add(pokemon)
                }
            }
            currentNumber += 1
            updatePosition(currentRegion.value!!)
            return@async getListByRegion(currentRegion.value!!)
        }
        //update pokemonList through currentRegionList
        pokemonList.postValue(list.await())
        isLoading = false
    }

    private suspend fun getPokemonInfo(id: Int): Deferred<Any?> = withContext(Dispatchers.IO) {
        async {
            repository.getPokemonInfoById(
                    id = id,
                    onFetchFailed = { t ->
                        t.stackTrace //todo もっとなんかsnackBarとかで表示させたい
                    })?.data
        }
    }

    private suspend fun getPokemonSpecies(id: Int): Deferred<Any?> = withContext(Dispatchers.IO) {
        async {
            repository.getPokemonSpeciesById(
                    id = id,
                    onFetchFailed = { t ->
                        t.stackTrace //todo もっとなんかsnackBarとかで表示させたい
                    })?.data
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

    private fun getPositionByRegion(region: Region): Int {
        return when(region){
            Region.Kanto -> positionKanto
            Region.Johto -> positionJohto
            Region.Hoenn -> positionHoenn
            Region.Sinnoh -> positionSinnoh
            Region.Unova -> positionUnova
            Region.Kalos -> positionKalos
            Region.Alola -> positionAlola
            Region.Galar -> positionGalar
        }
    }
    private fun updatePosition(region: Region) {
        when(region){
            Region.Kanto -> positionKanto += 1
            Region.Johto -> positionJohto += 1
            Region.Hoenn -> positionHoenn += 1
            Region.Sinnoh -> positionSinnoh += 1
            Region.Unova -> positionUnova += 1
            Region.Kalos -> positionKalos += 1
            Region.Alola -> positionAlola += 1
            Region.Galar -> positionGalar += 1
        }
    }
}
