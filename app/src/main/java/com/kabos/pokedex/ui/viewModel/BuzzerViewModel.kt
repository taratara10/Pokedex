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

    fun generateQuestionIdList() {
        val range = mutableListOf<Int>() //Regionの範囲
        val taken = mutableListOf<Int>() //完成品　ランダムに選択した要素を持たせる

        //regionのList<Int>を生成
        for (i in currentRegion.start..currentRegion.end) {
            range.add(i)
        }

        repeat(numberOfQuestion){
            //rangeの範囲で要素をランダムに取得してtakenに持たせる
            val rangeSize = range.size
            val index = Random.nextInt(rangeSize)
            taken += range[index]

            //全部shuffle()は無駄なので、末尾とindexを置換する
            val lastIndex = rangeSize -1
            val lastElement = range.removeAt(lastIndex) //末尾を削除する
            if (index < lastIndex) range[index] = lastElement //indexが末尾でなければ置換する
        }
        questionIdList = taken as ArrayList<Int>
    }










}