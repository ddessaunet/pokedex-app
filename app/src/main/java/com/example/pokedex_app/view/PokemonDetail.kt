package com.example.pokedex_app.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pokedex_app.R

class PokemonDetail : Fragment() {

    /*
    * If you are familiar with Java, you may relate companion objects with static methods
    * (even though how they work internally is totally different). The companion objects
    * can access private members of the class. Hence, they can be used to implement the
    * factory method patterns.
    *
    * https://www.programiz.com/kotlin-programming/companion-objects
    * */
    companion object {
        internal var instance:PokemonDetail?=null

        fun getInstance():PokemonDetail {
            if (instance == null) {
                instance = PokemonDetail()
            }
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)

        return itemView
    }


}
