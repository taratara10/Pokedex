package com.kabos.pokedex.ui.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.pokedex.R
import com.kabos.pokedex.model.Pokemon
import com.kabos.pokedex.model.PokemonInfo
import com.kabos.pokedex.model.PokemonSpecies
import com.kabos.pokedex.model.Region
import com.kabos.pokedex.repository.PokemonRepository
import com.kabos.pokedex.util.QuestionsRadio
import com.kabos.pokedex.util.RegionCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class BuzzerViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel(){

    var currentPokemon: MutableLiveData<Pokemon> = MutableLiveData()
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
    var playerNoneChecked: Boolean = false
    var isAnswered :Boolean = false
    var goResultFragment = MutableLiveData(false)

    var buttonText = MutableLiveData(R.string.next_btn)

    var questionsRadioChecked = MutableLiveData(QuestionsRadio.secound)


    fun updateQuestionsNumber() {
        numberOfQuestion = questionsRadioChecked.value?.number!!
    }

    fun isDisplayPlayerImage(id: Int): Int {
        return if (id <= numberOfPlayer) View.VISIBLE else View.GONE
    }




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
        goResultFragment.postValue(false)
        Log.d("startQuestion","${questionIdList.first()}/${currentPokemon.value}")
    }

    fun setupNextQuestion(){
        Log.d("setupNext","launch setupNextQuestion /playerOne${playerOneChecked}/")
        if (!isAnswered) return
        countPlayerScore()
        currentProgress ++
        getPokemon(questionIdList[currentProgress + 1])
        //最終問題ならbuttonTextを差し替え
        if (currentProgress == numberOfQuestion) buttonText.postValue(R.string.finish_btn)
    }

    fun atLastQuestion(){
        countPlayerScore()
        //buzzerQuizFragmentに通知を送って、navigationをFragmentで処理
        goResultFragment.postValue(true)

    }

    //checkboxをclickでnext判定
    //todo btn alphaを調節
    fun isAnsweredAnyPlayer(){
        isAnswered = playerOneChecked
                || playerTwoChecked
                || playerThreeChecked
                || playerFourChecked
                || playerFiveChecked
                || playerSixChecked
                || playerNoneChecked
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
        val pokemon = async {
            return@async repository.mergePokemonData(
                    getPokemonInfo(id).await() as PokemonInfo,
                    getPokemonSpecies(id).await() as PokemonSpecies)
        }
        currentPokemon.postValue(pokemon.await())
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




}