package com.kabos.pokedex.model


data class PokemonSpecies(
    val id: Int,
    val names: List<Name>,
    val flavor_text_entries: List<FlavorTextEntry>,
    val genera: List<Genera>,
)


//フシギダネ
data class Name(
    val name: String
)

//"うまれたときから　せなかに\nふしぎな　タネが　うえてあって\nからだと　ともに　そだつという。"
data class FlavorTextEntry(
    val flavor_text: String,
    val language: Language,
    val version: Version //x, sword
)

//ja-Hrkt
data class Language(
    val name: String
)


//〇〇ポケモン
data class Genera(
    val genus: String,
    val language: LanguageX
)

data class LanguageX(
    val name: String,
    val url: String
)

data class Version(
    val name: String
)
