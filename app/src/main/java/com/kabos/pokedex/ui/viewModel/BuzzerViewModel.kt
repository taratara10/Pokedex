package com.kabos.pokedex.ui.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kabos.pokedex.R
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.Region
import com.kabos.pokedex.repository.PokemonRepository
import com.kabos.pokedex.util.QuestionsRadio
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class BuzzerViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel(){

    var currentRegion: Region = Region.Kanto
    var currentPokemon: Pokemon? = null
    var currentProgress: Int = 1
    var numberOfQuestion: Int = 10
    var numberOfPlayer:Int = 2
    var questionIdList = arrayListOf<Int>()

    var questionsRadioChecked = MutableLiveData(QuestionsRadio.secound)


    fun updateQuestionsNumber() {
        numberOfQuestion = questionsRadioChecked.value?.number!!
    }

    fun isDisplayPlayerImage(id: Int): Int {
        return if (id <= numberOfPlayer) View.VISIBLE else View.GONE
    }



    //fun createRandomIdList():List<Int>

    //previousPokemon -> currentPokemon

    private fun generateQuestionIdList() {
        val range = mutableListOf<Int>() //Regionの範囲
        for (i in currentRegion.start..currentRegion.end) {
            range.add(i)
        }
        range.shuffle()
        questionIdList = range.take(numberOfQuestion) as ArrayList<Int>
    }

    fun startQuestion(){
        generateQuestionIdList()

    }








}