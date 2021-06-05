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
    val isLastQuestion = MutableLiveData(false)

    var playerOneScore: Int = 0
    var playerTwoScore: Int = 0
    var playerThreeScore: Int = 0
    var playerFourScore: Int = 0
    var playerFiveScore: Int = 0
    var playerSixScore: Int = 0

    var playerOneChecked: Boolean = false
    var playerTwoChecked: Boolean = false
    var playerThreeChecked: Boolean = false
    var playerFourChecked: Boolean = false
    var playerFiveChecked: Boolean = false
    var playerSixChecked: Boolean = false
    var isAnswered :Boolean = false

    var buttonText = MutableLiveData(R.string.next_btn)

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

    fun setupNextQuestion(){
        if (!isAnswered) return
        countPlayerScore()
        currentProgress ++
        getPokemon(questionIdList[currentProgress - 1])
        if (currentProgress == numberOfQuestion) buttonText.postValue(R.string.finish_btn)
    }

    fun atLastQuestion(){
        countPlayerScore()
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

    private fun countPlayerScore(){
        if (playerOneChecked) {
            playerOneScore ++
            playerOneChecked = false
        }
        if (playerTwoChecked) {
            playerTwoScore ++
            playerThreeChecked = false
        }
        if (playerThreeChecked) {
            playerThreeScore ++
            playerThreeChecked = false
        }
        if (playerFourChecked) {
            playerFourScore ++
            playerFiveChecked = false
        }
        if (playerFiveChecked) {
            playerFiveScore ++
            playerFiveChecked = false
        }
        if (playerSixChecked) {
            playerSixScore ++
            playerSixChecked = false
        }
    }

    private fun getPokemon(id: Int) = viewModelScope.launch{
        //currentPokemon.postValue()
    }





}