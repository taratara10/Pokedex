package com.kabos.pokedex.model

enum class Region(val start: Int, val end: Int, val regionName: String) {
    Kanto(1,151, "カントー"),//Red,Green
    Johto(152,251, "ジョウト"), //2 HGSS
    Hoenn(252,386,"ホウエン"), //3 Ruby,Sapphire
    Sinnoh(387,494,"シンオウ"),//4 DP
    Unova(495,649, "イッシュ"), //5 イッシュBW
    Kalos(650,721,"カロス"), //6 XY
    Alola(722,809,"アローラ"), //7 SM
    Galar(810,898,"ガラル") //8 SS
}