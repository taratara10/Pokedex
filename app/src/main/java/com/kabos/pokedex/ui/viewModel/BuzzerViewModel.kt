package com.kabos.pokedex.ui.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
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
import net.cachapa.expandablelayout.ExpandableLayout
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class BuzzerViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel(){

    var currentPokemon: MutableLiveData<Pokemon> = MutableLiveData()
    var currentRegion: Region = Region.Kanto
    var currentProgress = MutableLiveData(1)
    var numberOfQuestion: Int = 10
    var numberOfPlayer:Int = 2

    var questionIdList = arrayListOf<Int>()

    //6人分のスコア [0]が不正解
    var playerScoreList = mutableListOf(0f, 0f, 0f, 0f, 0f, 0f)

    //LiveData<MutableList>にしてもいいんだけど、処理が面倒なので個別の変数で運用する
    var playerOneChecked = MutableLiveData(false)
    var playerTwoChecked = MutableLiveData(false)
    var playerThreeChecked = MutableLiveData(false)
    var playerFourChecked = MutableLiveData(false)
    var playerFiveChecked = MutableLiveData(false)
    var playerSixChecked = MutableLiveData(false)
    var playerNoneChecked = MutableLiveData(false)
    var isAnswered :Boolean = false
    var goResultFragment = MutableLiveData(false)

    var buttonText = MutableLiveData(R.string.next_btn)

    var isCollapseCardView = MutableLiveData(false)
    var isCheckedNumberOfQuestionRadio = MutableLiveData(QuestionsRadio.secound)






    private fun generateQuestionIdList() {
        val range = mutableListOf<Int>() //Regionの範囲
        for (i in currentRegion.start..currentRegion.end) {
            range.add(i)
        }
        range.shuffle()
        questionIdList = range.take(numberOfQuestion) as ArrayList<Int>
    }


    /**
     *   btnBuzzerNext click listener
     */
    fun startQuestion(){
        currentProgress.postValue(1)
        generateQuestionIdList()
        getPokemon(questionIdList.first())
        goResultFragment.postValue(false)
    }

    fun setupNextQuestion(){
        if (currentProgress.value != numberOfQuestion) {
            updateQuestion()
        } else {
            navigateResultFragment()
        }
    }

    private fun updateQuestion(){
        //checkboxが空ならreturn
        if (!isAnswered) return

        //fragment側で検知してcardを閉じる
        isCollapseCardView.postValue(true)
        countPlayerScore()
        incrementCurrentProgress()
        getPokemon(questionIdList[currentProgress.value as Int -1])
        //最終問題ならbuttonTextを差し替え
        if (currentProgress.value == numberOfQuestion) {
            buttonText.postValue(R.string.finish_btn)
        }
    }

    private fun navigateResultFragment(){
        countPlayerScore()
        //buzzerQuizFragmentに通知を送って、navigationをFragmentで処理
        goResultFragment.postValue(true)

    }

    private fun incrementCurrentProgress() {
        currentProgress.value?.let { i ->
            if (i < numberOfQuestion) currentProgress.value = i + 1
        }
    }

    /**
     * layout callback
     */
    //buzzer main fragment
    fun updateNumberOfQuestion() {
        numberOfQuestion = isCheckedNumberOfQuestionRadio.value?.number!!
    }

    fun isDisplayPlayerImage(id: Int): Int {
        return if (id <= numberOfPlayer) View.VISIBLE else View.GONE
    }
    //checkboxをclickでnext判定
    //todo btn alphaを調節
    fun isAnsweredAnyPlayer(){
        isAnswered = playerOneChecked.value!!
                || playerTwoChecked.value!!
                || playerThreeChecked.value!!
                || playerFourChecked.value!!
                || playerFiveChecked.value!!
                || playerSixChecked.value!!
                || playerNoneChecked.value!!
    }

    private fun countPlayerScore(){
        if (playerOneChecked.value!!) {
            playerScoreList[0] ++
            playerOneChecked.postValue(false)
        }
        if (playerTwoChecked.value!!) {
            playerScoreList[1] ++
            playerTwoChecked.postValue(false)
        }
        if (playerThreeChecked.value!!) {
            playerScoreList[2] ++
            playerThreeChecked.postValue(false)
        }
        if (playerFourChecked.value!!) {
            playerScoreList[3] ++
            playerFourChecked.postValue(false)
        }
        if (playerFiveChecked.value!!) {
            playerScoreList[4] ++
            playerFiveChecked.postValue(false)
        }
        if (playerSixChecked.value!!) {
            playerScoreList[5] ++
            playerSixChecked.postValue(false)
        }
        if (playerNoneChecked.value!!){
            playerNoneChecked.postValue(false)
        }
    }

    /**
     * getPokemon
     */
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