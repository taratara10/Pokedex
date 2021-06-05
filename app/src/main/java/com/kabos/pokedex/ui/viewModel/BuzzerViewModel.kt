package com.kabos.pokedex.ui.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.pokedex.R
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.Region
import com.kabos.pokedex.repository.PokemonRepository
import com.kabos.pokedex.util.QuestionsRadio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class BuzzerViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel(){

    lateinit var currentPokemon: MutableLiveData<Pokemon>
//    lateinit var nextPokemon: Pokemon
    var currentRegion: Region = Region.Kanto
    var currentProgress: Int = 1
    var numberOfQuestion: Int = 10
    var numberOfPlayer:Int = 2

    var questionIdList = arrayListOf<Int>()

    var playerOne: Int = 0
    var playerTwo: Int = 0
    var playerThree: Int = 0
    var playerFour: Int = 0
    var playerFive: Int = 0
    var playerSix: Int = 0

    var playerOneChecked: Boolean = false
    var playerTwoChecked: Boolean = false
    var playerThreeChecked: Boolean = false
    var playerFourChecked: Boolean = false
    var playerFiveChecked: Boolean = false
    var playerSixChecked: Boolean = false
    var isAnswered :Boolean = false

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
        getPokemon(questionIdList.first())
    }

    //名前を動詞にする
    fun nextQuestion(){
        if (!isAnswered) return


    }

    //checkboxをclickでnext判定
    fun isAnswered() {
        isAnswered = playerOneChecked ||
                playerTwoChecked ||
                playerThreeChecked ||
                playerFourChecked ||
                playerFiveChecked ||
                playerSixChecked
    }

    private fun getPokemon(id: Int) = viewModelScope.launch{
        //currentPokemon.postValue()
    }





}