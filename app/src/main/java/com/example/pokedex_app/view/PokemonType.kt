package com.example.pokedex_app.view


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.pokedex_app.R
import com.example.pokedex_app.adapter.PokemonListAdapter
import com.example.pokedex_app.common.Common
import com.example.pokedex_app.common.ItemOffsetDecoration
import com.example.pokedex_app.model.Pokemon
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.fragment_pokemon_list.*

/**
 * A simple [Fragment] subclass.
 */
class PokemonType : Fragment() {

    internal lateinit var recycler_view: RecyclerView

    internal lateinit var adapter: PokemonListAdapter
    internal lateinit var search_adapter: PokemonListAdapter
    internal var last_suggest:MutableList<String> = ArrayList()

    internal lateinit var search_bar: MaterialSearchBar

    internal lateinit var typeList:List<Pokemon>

    companion object {
        internal var instance:PokemonType?=null

        fun getInstance():PokemonType {
            if (instance == null) {
                instance = PokemonType()
            }
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var itemView = inflater.inflate(R.layout.fragment_pokemon_type, container, false)

        recycler_view = itemView.findViewById(R.id.pokemon_recyclerview) as RecyclerView
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(activity, 2)
        val itemDecoration = ItemOffsetDecoration(activity!!, R.dimen.spacing)
        recycler_view.addItemDecoration(itemDecoration)

        //Setup Search Bar
        search_bar = itemView.findViewById(R.id.search_bar) as MaterialSearchBar
        search_bar.setHint("Enter Pokemon Name")
        search_bar.setCardViewElevation(10)

        search_bar.addTextChangeListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val suggest = ArrayList<String>()
                for (search in last_suggest) {
                    if (search.toLowerCase().contains(search_bar.text.toLowerCase())) {
                        suggest.add(search)
                    }
                }
                search_bar.lastSuggestions = suggest
            }

        })
        search_bar.setOnSearchActionListener(object: MaterialSearchBar.OnSearchActionListener {
            override fun onButtonClicked(buttonCode: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSearchStateChanged(enabled: Boolean) {
                if (!enabled) {
                    pokemon_recyclerview.adapter = adapter
                }
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                startSearch(text.toString())
            }

        })

        if (arguments != null)
        {
            val type = arguments!!.getString("type")
            if (type != null)
            {
                typeList = Common.findPokemonByType(type)
                adapter = PokemonListAdapter(activity!!, typeList)
                recycler_view.adapter = adapter

                loadSuggest()
            }

        }

        return itemView
    }

    private fun loadSuggest() {
        last_suggest.clear()
        if (typeList.size > 0)
            for (pokemon in typeList)
                last_suggest.add(pokemon.name!!)
        search_bar!!.lastSuggestions = last_suggest
    }

    private fun startSearch(text: String) {
        if (typeList.size > 0) {
            val result = ArrayList<Pokemon>()
            for (pokemon in typeList) {
                if (pokemon.name!!.toLowerCase().contains(text.toLowerCase())) {
                    result.add(pokemon)
                }
            }
            search_adapter = PokemonListAdapter(activity!!, result)
            pokemon_recyclerview.adapter = search_adapter
        }

    }

}
