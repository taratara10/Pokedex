package com.kabos.pokedex.ui.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabos.pokedex.R
import com.kabos.pokedex.model.*
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
    var numberOfCorrectAnswer: Int = 0

    var questionIdList = mutableListOf<Int>() //正解のid
    var wrongChoicesList = mutableListOf<Int>() //問題数 x3 の選択肢のid
    var currentChoices:MutableLiveData<List<QuizChoice>> = MutableLiveData() //QuizChoice型の4択

    //UI parameter
    var goResultFragment = MutableLiveData(false)
    var buttonText = MutableLiveData(R.string.next_btn)
    var isDisplayAnswer = MutableLiveData(false)
    var isBtnEnable = MutableLiveData(false)
    var isCollapseCardView = MutableLiveData(false)
    var isCheckedNumberOfQuestionRadio = MutableLiveData(QuestionsRadio.secound)

    //four choices card background color
    private val unSelectedColor = R.color.white
    val correctColor = R.color.green_a400
    val wrongColor = R.color.red_a200


    //default value
    var _isDisplayImageCorrect = mutableListOf(View.GONE, View.GONE, View.GONE, View.GONE)
    var _isDisplayImageWrong = mutableListOf(View.GONE, View.GONE, View.GONE, View.GONE)
    var _isTransparentChoice = mutableListOf(1f, 1f, 1f, 1f)
    var _isNotTransparentChoice = mutableListOf(0.5f, 0.5f, 0.5f, 0.5f)
    var _isDisplayChoiceBackground = mutableListOf(unSelectedColor, unSelectedColor, unSelectedColor, unSelectedColor)
    //displayed card status
    var isDisplayImageCorrect = MutableLiveData<List<Int>>(_isDisplayImageCorrect)
    var isDisplayImageWrong = MutableLiveData<List<Int>>(_isDisplayImageWrong)
    var isTransparentChoice = MutableLiveData<List<Float>>(_isTransparentChoice)
    var isDisplayChoiceBackground = MutableLiveData<List<Int>>(_isDisplayChoiceBackground)


    private fun generateQuestionIdList() {
        //regionのstart..endのidListを生成
        val range = mutableListOf<Int>()
        for (i in currentRegion.value!!.start .. currentRegion.value!!.end)  range.add(i)

        //question分のelementをランダムに取得
        range.shuffle()
        questionIdList = range.take(numberOfQuestion) as MutableList<Int>

        //fourChoices用の選択肢をquestion x 3取得. answerは選択肢から除く
        range.removeAll(questionIdList)
        wrongChoicesList = range.take(numberOfQuestion * 3) as MutableList<Int>
    }


    /**
     * btnNext ClickListener
     */

    fun startQuestion() {
        //reset value
        currentProgress.postValue(1)
        numberOfCorrectAnswer = 0

        generateQuestionIdList()
        updateCurrentChoices()
        goResultFragment.postValue(false)
        buttonText.postValue(R.string.next_btn)
    }

    fun setupNextQuestion() {
        if (currentProgress.value != numberOfQuestion) {
            updateQuestion()
        } else {
            goResultFragment.postValue(true)
        }
    }

    private fun updateQuestion() {
        //fragment側で検知してcardを閉じる
        isCollapseCardView.postValue(true)
        isDisplayAnswer.postValue(false)
        isBtnEnable.postValue(false)

        isDisplayChoiceBackground.postValue(_isDisplayChoiceBackground)
        incrementCurrentProgress()
        updateCurrentChoices()
        //最終問題ならbuttonTextを差し替え
        if (currentProgress.value == numberOfQuestion) {
            buttonText.postValue(R.string.finish_btn)
        }
    }

    private fun updateCurrentChoices() =viewModelScope.launch {
        //idを4つ生成
        val correctChoice:Int = questionIdList[currentProgress.value as Int - 1]
        val wrongChoices = wrongChoicesList.take(3)
        wrongChoicesList = wrongChoicesList.drop(3) as MutableList<Int>
        //4つの選択肢のリスト(id)
        val currentChoicesIdList = (wrongChoices + correctChoice) as MutableList<Int>
        currentChoicesIdList.shuffle()

        //update currentPokemon
        getPokemon(correctChoice)

        //4つの選択肢を取得(pokemonName)
        val choiceDataList = mutableListOf<QuizChoice>()
        for (id in 0..3){
            val pokemonSpecies = getPokemonSpecies(currentChoicesIdList[id]).await() as PokemonSpecies
            val isCorrect: Boolean = correctChoice == currentChoicesIdList[id]
            val choice = QuizChoice(index = id, name = pokemonSpecies.names[0].name, correct = isCorrect )
            choiceDataList.add(choice)
        }
        currentChoices.postValue(choiceDataList)
    }

    private fun incrementCurrentProgress() {
        currentProgress.value?.let { i ->
            if (i < numberOfQuestion) currentProgress.value = i + 1
        }
    }

    /**
     * layout callback
     */
    //main fragment
    fun updateNumberOfQuestion() {
        numberOfQuestion = isCheckedNumberOfQuestionRadio.value?.number!!
    }

    //quiz fragment

    //fragment_four_choices_quizから onClick="@{(position) -> VM.checkAnswer()}"で呼び出す
    //選択肢が正解なら、正解数++
    //isDisplayAnswer をFourChoicesQuizでobserveして正誤の背景を変更
    fun checkTheAnswer(selectedPosition: Int) {
        //find correct position
        var correctPosition = 0
        for (i in currentChoices.value!!.indices){
            if (currentChoices.value?.get(i)!!.correct) correctPosition = i
        }

        //display correct image
        val correct = _isDisplayImageCorrect.toMutableList() //toで新しいインスタンスを生成する
        correct[correctPosition] = View.VISIBLE
        isDisplayImageCorrect.postValue(correct)


        //transparent not selected choices
        val transparent = _isNotTransparentChoice.toMutableList()
        transparent[selectedPosition] = 1f
        transparent[correctPosition] = 1f
        isTransparentChoice.postValue(transparent)

        //display correct color
        val background = _isDisplayChoiceBackground.toMutableList()
        background[correctPosition] = correctColor

        //checkAnswer
        if (selectedPosition == correctPosition){
            numberOfCorrectAnswer ++
        }else{
            //display wrong image which player selected.
            val wrong = _isDisplayImageWrong.toMutableList()
            wrong[selectedPosition] = View.VISIBLE
            isDisplayImageWrong.postValue(wrong)

            //display wrong background
            background[selectedPosition] = wrongColor
        }
        isDisplayChoiceBackground.postValue(background)
        isDisplayAnswer.postValue(true)
        isBtnEnable.postValue(true)
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




