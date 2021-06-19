package com.kabos.pokedex.ui.viewModel

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
class FourChoiceViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {

    var currentPokemon: MutableLiveData<Pokemon> = MutableLiveData()
    var currentRegion: MutableLiveData<Region> = MutableLiveData(Region.Kanto)
    var currentProgress = MutableLiveData(1)
    var numberOfQuestion: Int = 10

    var questionIdList = mutableListOf<Int>() //正解のid
    var fourChoicesList = mutableListOf<Int>() //問題数 x3 の選択肢のid


    //UI parameter
    var isAnswered: Boolean = false
    var goResultFragment = MutableLiveData(false)
    var buttonText = MutableLiveData(R.string.next_btn)
    var isBtnEnable = MutableLiveData(false)
    var isCollapseCardView = MutableLiveData(false)
    var isCheckedNumberOfQuestionRadio = MutableLiveData(QuestionsRadio.secound)


    //buzzer main fragment
    fun updateNumberOfQuestion() {
        numberOfQuestion = isCheckedNumberOfQuestionRadio.value?.number!!
    }

    private fun generateQuestionIdList() {
        //regionのstart..endのidListを生成
        val range = mutableListOf<Int>()
        for (i in currentRegion.value!!.start .. currentRegion.value!!.end) {
            range.add(i)
        }

        //question分のelementをランダムに取得
        range.shuffle()
        questionIdList = range.take(numberOfQuestion) as MutableList<Int>

        //fourChoices用の選択肢をquestion x 3取得. answerは選択肢から除く
        range.removeAll(questionIdList)
        fourChoicesList = range.take(numberOfQuestion * 3) as MutableList<Int>
    }


    /**
     * btnNext ClickListener
     */

    fun startQuestion() {
        //reset value
        currentProgress.postValue(1)

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

    }

    private fun navigateResultFragment() {

    }

    /**
     * layout callback
     */
    private fun incrementCurrentProgress() {
        currentProgress.value?.let { i ->
            if (i < numberOfQuestion) currentProgress.value = i + 1
        }
    }

    fun isAnsweredAnyChoices() {


    }
    private fun toggleButtonContent() {
        if (!isAnswered) isBtnEnable.postValue(false)
        else isBtnEnable.postValue(true)
    }


    /**
     * getPokemon
     */
    private fun getPokemon(id: Int) = viewModelScope.launch {
        val pokemon = async {
            return@async repository.mergePokemonData(
                getPokemonInfo(id).await() as PokemonInfo,
                getPokemonSpecies(id).await() as PokemonSpecies
            )
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




