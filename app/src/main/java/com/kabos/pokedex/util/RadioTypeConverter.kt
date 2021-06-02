@file:JvmName("RadioTypeConverter")
package com.kabos.pokedex.util

import androidx.databinding.InverseMethod
import com.kabos.pokedex.R

fun buttonIdToType(selectedButtonId: Int): QuestionsRadio {
    return when (selectedButtonId) {
        R.id.rb_buzzer_questions_number_first -> QuestionsRadio.first
        R.id.rb_buzzer_questions_number_second -> QuestionsRadio.secound
        R.id.rb_buzzer_questions_number_third -> QuestionsRadio.third
        else -> QuestionsRadio.secound
    }
}

@InverseMethod("buttonIdToType")
fun typeToButtonId(radioType: QuestionsRadio): Int {
    return when (radioType) {
        QuestionsRadio.first -> R.id.rb_buzzer_questions_number_first
        QuestionsRadio.secound -> R.id.rb_buzzer_questions_number_second
        QuestionsRadio.third -> R.id.rb_buzzer_questions_number_third
    }
}


enum class QuestionsRadio(val number: Int){
    first(5),
    secound(10),
    third(15)
}
