package com.kabos.pokedex.model

import com.kabos.pokedex.R

enum class TypeData(val japanese: String, val image:Int) {
    Bug("むし", R.drawable.type_bug),
    Dark("あく", R.drawable.type_dark),
    Dragon("ドラゴン", R.drawable.type_dragon),
    Electric("でんき", R.drawable.type_electric),
    Fairy("フェアリー", R.drawable.type_fairy),
    Fighting("かくとう", R.drawable.type_flying),
    Fire("ほのお", R.drawable.type_fire),
    Flying("ひこう", R.drawable.type_flying),
    Ghost("ゴースト", R.drawable.type_ghost),
    Grass("くさ", R.drawable.type_grass),
    Ground("じめん", R.drawable.type_ground),
    Ice("こおり", R.drawable.type_ice),
    Normal("ノーマル", R.drawable.type_normal),
    Poison("どく", R.drawable.type_poison),
    Psychic("エスパー", R.drawable.type_psychic),
    Rock("いわ", R.drawable.type_rock),
    Steel("はがね", R.drawable.type_steel),
    Water("みず", R.drawable.type_water)
}