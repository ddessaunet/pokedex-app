package com.example.pokedex_app.common

import android.graphics.Color
import com.example.pokedex_app.model.Pokemon

object Common {
    var pokemonList:List<Pokemon> = ArrayList()
    var KEY_ENABLE_HOME:String = "position"
    var KEY_NUM_EVOLUTION:String = "evolution"
    var KEY_POKEMON_TYPE:String = "type"

    fun findPokemonByNum(num: String?):Pokemon? {
        for (pokemon in Common.pokemonList)
            if (pokemon.num.equals(num!!.toInt()))
                return pokemon
        return null
    }

    fun findPokemonByType(type: String?): List<Pokemon> {
        val pokemonList = ArrayList<Pokemon>()
        for (pokemon in Common.pokemonList)
            if (pokemon.type!!.contains(type))
                pokemonList.add(pokemon)
        return pokemonList
    }

    fun getColorByType(type: String): Int {
        when (type) {
            "Normal" -> return Color.parseColor("#A4A27A")
            "Dragon" -> return Color.parseColor("#743BFB")
            "Psychic" -> return Color.parseColor("#F15B85")
            "Electric" -> return Color.parseColor("#E9CA3C")
            "Ground" -> return Color.parseColor("#D9BF6C")
            "Grass" -> return Color.parseColor("#81C85B")
            "Poison" -> return Color.parseColor("#A441A3")
            "Steel" -> return Color.parseColor("#BAB7D2")
            "Fairy" -> return Color.parseColor("#DDA2DF")
            "Fight" -> return Color.parseColor("#BE3027")
            "Bug" -> return Color.parseColor("#A8B822")
            "Ghost" -> return Color.parseColor("#705693")
            "Dark" -> return Color.parseColor("#745945")
            "Ice" -> return Color.parseColor("#9BD8D8")
            "Water" -> return Color.parseColor("#658FF1")
            else -> return Color.parseColor("#658FA0")

        }

    }
}