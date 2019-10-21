package com.example.pokedex_app.common

import com.example.pokedex_app.model.Pokemon

object Common {
    var pokemonList:List<Pokemon> = ArrayList()
    var KEY_ENABLE_HOME:String = "position"

    fun findPokemonByNum(num: String?):Pokemon? {
        for (pokemon in Common.pokemonList)
            if (pokemon.num.equals(num))
                return pokemon
        return null
    }
}