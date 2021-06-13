package com.kabos.pokedex.model

import com.kabos.pokedex.R

enum class TypeData(val japanese: String, val image: String) {
    Bug("むし", R.drawable.type_bug.toString()),
    Dark("あく", R.drawable.type_dark.toString()),
    Dragon("ドラゴン", R.drawable.type_dragon.toString()),
    Electric("でんき", R.drawable.type_electric.toString()),
    Fairy("フェアリー", R.drawable.type_fairy.toString()),
    Fighting("かくとう", R.drawable.type_flying.toString()),
    Fire("ほのお", R.drawable.type_fire.toString()),
    Flying("ひこう", R.drawable.type_flying.toString()),
    Ghost("ゴースト", R.drawable.type_ghost.toString()),
    Grass("くさ", R.drawable.type_grass.toString()),
    Ground("じめん", R.drawable.type_ground.toString()),
    Ice("こおり", R.drawable.type_ice.toString()),
    Normal("ノーマル", R.drawable.type_normal.toString()),
    Poison("どく", R.drawable.type_poison.toString()),
    Psychic("エスパー", R.drawable.type_psychic.toString()),
    Rock("いわ", R.drawable.type_rock.toString()),
    Steel("はがね", R.drawable.type_steel.toString()),
    Water("みず", R.drawable.type_water.toString())
}