package com.kabos.pokedex.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel(){

    var pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var country:List<Int> = listOf()

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

        //insert pokemonList
    }

    private fun getPokemon(id: Int) = viewModelScope.launch {



        //pokemonInfoを取得

        //pokemonSpeciesを取得

        //merge Pokemon()

    }

}