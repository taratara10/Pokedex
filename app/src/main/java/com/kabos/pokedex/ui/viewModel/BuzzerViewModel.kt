package com.kabos.pokedex.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kabos.pokedex.R
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.Region
import com.kabos.pokedex.repository.PokemonRepository
import com.kabos.pokedex.util.QuestionsRadio
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BuzzerViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel(){

    var currentRegion: MutableLiveData<Region> = MutableLiveData(Region.Kanto)
    var currentPokemon: Pokemon? = null
    var currentProgress: Int = 1
    var questionsNum: MutableLiveData<Int> = MutableLiveData(10)

    var questionsNumRadioChecked = MutableLiveData(QuestionsRadio.secound)


    fun updateQuestionsNumber() {
        Log.d("buzzerrr","${questionsNumRadioChecked.value?.name}")

    }



















}