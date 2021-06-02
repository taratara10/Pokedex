package com.kabos.pokedex.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.Region
import com.kabos.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BuzzerViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel(){

    var currentRegion: MutableLiveData<Region> = MutableLiveData(Region.Kanto)
    var currentPokemon: Pokemon? = null
    var currentProgress: Int = 1
    var questionsNum: Int = 10
}