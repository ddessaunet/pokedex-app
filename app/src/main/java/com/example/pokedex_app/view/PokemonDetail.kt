package com.example.pokedex_app.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.pokedex_app.R
import com.example.pokedex_app.adapter.PokemonEvolutionAdapter
import com.example.pokedex_app.adapter.PokemonTypeAdapter
import com.example.pokedex_app.common.Common
import com.example.pokedex_app.model.Evolution
import com.example.pokedex_app.model.Pokemon

class PokemonDetail : Fragment() {

    /*
     * Normally, properties declared as having a non-null type must be initialized in the
     * constructor. However, fairly often this is not convenient. For example, properties
     * can be initialized through dependency injection, or in the setup method of a unit test.
     * In this case, you cannot supply a non-null initializer in the constructor, but you still
     * want to avoid null checks when referencing the property inside the body of a class.
     *
     * https://kotlinlang.org/docs/reference/properties.html
     */
    internal lateinit var pokemon_image:ImageView
    internal lateinit var pokemon_name:TextView
    internal lateinit var pokemon_height:TextView
    internal lateinit var pokemon_weight:TextView

    lateinit var recycler_type:RecyclerView
    lateinit var recycler_weakness:RecyclerView

    lateinit var recycler_prev_evolution:RecyclerView
    lateinit var recycler_next_evolution:RecyclerView

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

        val pokemon:Pokemon?
        if (arguments!!.getString("num") == null)
            pokemon = Common.pokemonList[arguments!!.getInt("position")]
            else
            pokemon = Common.findPokemonByNum(arguments!!.getString("num"))

        pokemon_image = itemView.findViewById(R.id.pokemon_image) as ImageView
        pokemon_name = itemView.findViewById(R.id.name) as TextView
        pokemon_height = itemView.findViewById(R.id.height) as TextView
        pokemon_weight = itemView.findViewById(R.id.weight) as TextView

        recycler_prev_evolution = itemView.findViewById(R.id.recycler_prev_evolution) as RecyclerView
        recycler_prev_evolution.setHasFixedSize(true)
        recycler_prev_evolution.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recycler_next_evolution = itemView.findViewById(R.id.recycler_next_evolution) as RecyclerView
        recycler_next_evolution.setHasFixedSize(true)
        recycler_next_evolution.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recycler_type = itemView.findViewById(R.id.recycler_type) as RecyclerView
        recycler_type.setHasFixedSize(true)
        recycler_type.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recycler_weakness = itemView.findViewById(R.id.recycler_weakness) as RecyclerView
        recycler_weakness.setHasFixedSize(true)
        recycler_weakness.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        setDetailPokemon(pokemon)

        return itemView
    }

    private fun setDetailPokemon(pokemon:Pokemon?) {

        //Load Image
        Glide.with(activity!!).load(pokemon!!.img).into(pokemon_image)

        pokemon_name.text = pokemon.name
        pokemon_height.text = "Height: " + pokemon.height
        pokemon_weight.text = "Weight: " + pokemon.weight

        val typeAdapter = PokemonTypeAdapter(activity!!, pokemon.type!!)
        recycler_type.adapter = typeAdapter

        val weaknessAdapter = PokemonTypeAdapter(activity!!, pokemon.weaknesses!!)
        recycler_weakness.adapter = weaknessAdapter

        if (pokemon.prev_evolution != null) {
            val prevEvolution = PokemonEvolutionAdapter(activity!!, pokemon.prev_evolution as List<Evolution>)
            recycler_prev_evolution.adapter = prevEvolution
        }
        if (pokemon.next_evolution != null) {
            val nextEvolution = PokemonEvolutionAdapter(activity!!, pokemon.next_evolution as List<Evolution>)
            recycler_next_evolution.adapter = nextEvolution
        }

    }
}
