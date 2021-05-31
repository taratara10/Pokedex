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
            //todo stop getPokemonList()
                isLoading = false
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
        //check loading
        if (isLoading)return@launch else isLoading = true
            val list = async{
                for (i in 1..5) {
                    //updateRegion()でisLoading = falseになると中断
                    if (currentNumber <= regionEndNumber && isLoading == true){
                        val pokemonInfo = getPokemonInfo(currentNumber).await()
                        val pokemonSpecies = getPokemonSpecies(currentNumber).await()

                        if (pokemonInfo != null && pokemonSpecies != null) {
                            val pokemon = repository.mergePokemonData(
                                    pokemonInfo as PokemonInfo,
                                    pokemonSpecies as PokemonSpecies
                            )
                            //pokemonListがLiveDataで直接addできないので、一旦listRegionにaddして最後にpost
                            getListByRegion(currentRegion.value!!).add(pokemon)

                        }
                       currentNumber += 1
                    }
                }
                return@async getListByRegion(currentRegion.value!!)
        }
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

}