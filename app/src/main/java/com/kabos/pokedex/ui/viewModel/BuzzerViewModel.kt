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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class BuzzerViewModel @Inject constructor(private val repository: PokemonRepository)
    : ViewModel() {

    var currentPokemon: MutableLiveData<Pokemon> = MutableLiveData()
    var currentRegion: MutableLiveData<Region> = MutableLiveData(Region.Kanto)
    var currentProgress = MutableLiveData(1)
    var numberOfQuestion: Int = 10
    var numberOfPlayer: Int = 2

    var questionIdList = arrayListOf<Int>()

    //6人分 + 不正解のスコア [playerOne, playerTwo .. None]
    var playerScoreList = mutableListOf(0f, 0f, 0f, 0f, 0f, 0f, 0f)
    var playerRanking = mutableListOf(1f, 1f, 1f, 1f, 1f, 1f, 1f)

    //LiveData<MutableList>にしてもいいんだけど、処理が面倒なので個別の変数で運用する
    var playerOneChecked = MutableLiveData(false)
    var playerTwoChecked = MutableLiveData(false)
    var playerThreeChecked = MutableLiveData(false)
    var playerFourChecked = MutableLiveData(false)
    var playerFiveChecked = MutableLiveData(false)
    var playerSixChecked = MutableLiveData(false)
    var playerNoneChecked = MutableLiveData(false)
    var isAnswered: Boolean = false
    var goResultFragment = MutableLiveData(false)

    var buttonText = MutableLiveData(R.string.next_btn)
    var isBtnEnable = MutableLiveData(false)

    var isCollapseCardView = MutableLiveData(false)
    var isCheckedNumberOfQuestionRadio = MutableLiveData(QuestionsRadio.secound)


    private fun generateQuestionIdList() {
        val range = mutableListOf<Int>() //Regionの範囲
        for (i in currentRegion.value!!.start .. currentRegion.value!!.end) {
            range.add(i)
        }
        range.shuffle()
        questionIdList = range.take(numberOfQuestion) as ArrayList<Int>
    }


    /**
     *   btnBuzzerNext click listener
     */
    fun startQuestion() {
        //reset value
        currentProgress.postValue(1)
        playerScoreList = mutableListOf(0f, 0f, 0f, 0f, 0f, 0f, 0f)
        playerRanking = mutableListOf(1f, 1f, 1f, 1f, 1f, 1f, 1f)
        //人数 + noneに整形
        playerScoreList = playerScoreList.take(numberOfPlayer + 1) as MutableList<Float>
        playerRanking = playerRanking.take(numberOfPlayer + 1) as MutableList<Float>


        generateQuestionIdList()
        getPokemon(questionIdList.first())
        goResultFragment.postValue(false)
        buttonText.postValue(R.string.next_btn)
    }

    fun setupNextQuestion() {
        //checkboxが空ならreturn
        if (!isAnswered) return else isAnswered = false
        if (currentProgress.value != numberOfQuestion) {
            updateQuestion()
        } else {
            navigateResultFragment()
        }
    }

    private fun updateQuestion() {
        //fragment側で検知してcardを閉じる
        isCollapseCardView.postValue(true)
        isBtnEnable.postValue(false)
        countPlayerScore()
        incrementCurrentProgress()
        getPokemon(questionIdList[currentProgress.value as Int - 1])
        //最終問題ならbuttonTextを差し替え
        if (currentProgress.value == numberOfQuestion) {
            buttonText.postValue(R.string.finish_btn)
        }
    }

    private fun navigateResultFragment() {
        countPlayerScore()
        calcRankingFromScore()
        //buzzerQuizFragmentに通知を送って、navigationをFragmentで処理
        goResultFragment.postValue(true)

    }


    /**
     * layout callback
     */
    private fun incrementCurrentProgress() {
        currentProgress.value?.let { i ->
            if (i < numberOfQuestion) currentProgress.value = i + 1
        }
    }

    //buzzer main fragment
    fun updateNumberOfQuestion() {
        numberOfQuestion = isCheckedNumberOfQuestionRadio.value?.number!!
    }

    fun isDisplayPlayerImage(id: Int): Int {
        return if (id <= numberOfPlayer) View.VISIBLE else View.GONE
    }

    //checkboxをclickでnext判定
    //todo btn alphaを調節
    fun isAnsweredAnyPlayer() {
        isAnswered = playerOneChecked.value!!
                || playerTwoChecked.value!!
                || playerThreeChecked.value!!
                || playerFourChecked.value!!
                || playerFiveChecked.value!!
                || playerSixChecked.value!!
                || playerNoneChecked.value!!
        toggleButtonContent()
    }

    private fun toggleButtonContent() {
        if (!isAnswered) isBtnEnable.postValue(false)
            else isBtnEnable.postValue(true)
    }

    private fun countPlayerScore() {
        if (playerNoneChecked.value!!) {
            playerScoreList[playerScoreList.lastIndex]++
            playerNoneChecked.postValue(false)
        }
        if (playerOneChecked.value!!) {
            playerScoreList[0]++
            playerOneChecked.postValue(false)
        }
        if (playerTwoChecked.value!!) {
            playerScoreList[1]++
            playerTwoChecked.postValue(false)
        }
        if (playerThreeChecked.value!!) {
            playerScoreList[2]++
            playerThreeChecked.postValue(false)
        }
        if (playerFourChecked.value!!) {
            playerScoreList[3]++
            playerFourChecked.postValue(false)
        }
        if (playerFiveChecked.value!!) {
            playerScoreList[4]++
            playerFiveChecked.postValue(false)
        }
        if (playerSixChecked.value!!) {
            playerScoreList[5]++
            playerSixChecked.postValue(false)
        }
    }


    /**
     * barChartの処理
     */

    private fun calcRankingFromScore() {
        //noneはrankingにカウントしない
        val onlyPlayerList = playerScoreList.dropLast(1)
        //rankingの初期値1で、自分より大きな数があれば順位 +1
        for (i in onlyPlayerList.indices) {
            for (j in onlyPlayerList.indices) {
                if (onlyPlayerList[j] > onlyPlayerList[i]) playerRanking[i]++
            }
        }
    }

    fun isDisplayKingsRock(id: Int): Int {
        //noneは順位に含めない
        val onlyPlayerList = playerScoreList.dropLast(1)
        val maxPlayerScore = onlyPlayerList.maxOrNull()
        //同率もあるので、listで返す
        val topPlayerList = onlyPlayerList.mapIndexedNotNull { index, element -> if (element == maxPlayerScore) index else null }
        Log.d("kingsRock", "${maxPlayerScore}/${topPlayerList}")
        return if (topPlayerList.contains(id)) View.VISIBLE else View.GONE
    }


    /**
     * getPokemon
     */
    private fun getPokemon(id: Int) = viewModelScope.launch {
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
